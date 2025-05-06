#include <WiFi.h>

const char* ssid = "neon1024";
const char* password = "13371337";

WiFiServer server(80);
#define PIR_PIN 13

String lastStatus = "";
WiFiClient activeClient;

void setup() {
  Serial.begin(115200);
  pinMode(PIR_PIN, INPUT);

  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi connected.");
  Serial.print("ESP32 IP address: ");
  Serial.println(WiFi.localIP());

  server.begin();
}

void loop() {
  // 1. Accept new client if none is connected
  if (!activeClient || !activeClient.connected()) {
    WiFiClient client = server.available();
    if (client) {
      Serial.println("New SSE client connected");

      // Read request line
      String reqLine = client.readStringUntil('\r');
      client.read(); // skip \n

      // Respond only to GET /events
      if (reqLine.indexOf("GET /events") >= 0) {
        client.println("HTTP/1.1 200 OK");
        client.println("Content-Type: text/event-stream");
        client.println("Cache-Control: no-cache");
        client.println("Connection: keep-alive");
        client.println();
        activeClient = client;
        lastStatus = "";  // reset status so it sends update immediately
      } else {
        client.println("HTTP/1.1 404 Not Found");
        client.println("Content-Type: text/plain");
        client.println("Connection: close");
        client.println();
        client.println("Not found");
        client.stop();
      }
    }
  }

  // 2. Check PIR status and send only when it changes
  int motion = digitalRead(PIR_PIN);
  String currentStatus = (motion == HIGH) ? "motion" : "nomotion";

  if (activeClient && activeClient.connected() && currentStatus != lastStatus) {
    Serial.println("Status changed: " + currentStatus);
    activeClient.print("data: ");
    activeClient.print(currentStatus);
    activeClient.print("\n\n");
    lastStatus = currentStatus;
  }

  delay(100);
}