Authors: Ioan Robert Scumpu, Cristi Popan

Title: Motion detector using ESP32 with PIR sensor

Description: Motion detector using the ESP32 board with the HC-SR501 PIR sensor. Detects motion and displays a corresponding message via mobile. Scans continuously and displays the real time detection status. I chose the ESP32 board because it has built in WiFi and Bluetooth capabilities.

Components:
Board:
- ESP32 (https://sigmanortec.ro/placa-de-dezvoltare-esp32-dual-core-wifi-bluetooth-4mb)
Breadboard:
- 400 holes (https://sigmanortec.ro/Breadboard-400-puncte-p129872825)
Cables:
- x40 Dupont Male to male (https://sigmanortec.ro/40-Fire-Dupont-30cm-Tata-Tata-p210849599)
- x40 Dupong Male to female (https://ardushop.ro/ro/electronica/1919-40-x-fire-dupont-mama-tata-30cm-6427854029126.html?gad_source=1&gad_campaignid=22058879462&gbraid=0AAAAADlKU-6x44ivoDEoBCGUVN_4IQH23&gclid=Cj0KCQjw5ubABhDIARIsAHMighbTyAFjUQpcQ7e0XvMZlrxA_Kn6a7e_JRjezxvBCpwZ9A8DGKUaJ4IaAtGCEALw_wcB)
- HC-SR501 PIR Sensor (https://sigmanortec.ro/Senzor-PIR-miscare-p126182136?SubmitCurrency=1&id_currency=2&gad_source=1&gad_campaignid=22174019478&gbraid=0AAAAAC3W72OOli9hYN2xYidKMkS5DEyit&gclid=Cj0KCQjw5ubABhDIARIsAHMighYKoSNvw91BQSP1uDQenNGT7D5R34E0ZYsa3R0UlkSDeFyXt-J6JP0aAnDuEALw_wcB)
- Micro USB (https://sigmanortec.ro/cablu-date-smart-usb-la-micro-usb-fast-charging)

Software:
- Arduino IDE (https://www.arduino.cc/en/software/)
- Android Studio
- ESP32 for Arduino IDE tutorial (https://randomnerdtutorials.com/installing-the-esp32-board-in-arduino-ide-windows-instructions/)

Setup and build:
- Connect the board pins to the breadboard
- Connect 1 male to male cable to the 3V3 hole for the board and to the corresponding "-" hole
- Connect 1 male to male cable to the GND hole for the board and to the corresponding "+" hole
- Connect 1 male to female cable to the VCC hole on the PIR sensor to the 3V3 hole on the ESP32 board
- Connect 1 male to female cable to the GND hole on the PIR sensor to the GND hole on the ESP32 board
- Connect 1 male to female cable to the OUT hole on the PIR sensor to an output hole on the ESP32 board (here is 13)
- Connect the board to the Micro USB cable and to the device

Running:
- The board should go automatically in flash mode when uploading the code via Arduino IDE, but in case it doesn't, follow these steps:
- Hold the RESET button before and during the uploading via Arduino IDE until the "Connecting ....." message appears
- Go to the Serial Monitor in order to see the status of the motion detection.
- Connect your phone to the same network as the board and open the application.