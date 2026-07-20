FinTech App

An Android app (Kotlin) for personal finance tracking — register income/expense transactions and view your statement (extrato).

Features


Register new transactions (credit/debit)
View transaction history / statement
Local persistence with Room database
Simple, focused UI (no backend required)


Tech Stack


Kotlin
Android Views (Activities + XML layouts)
Room (local database)
RecyclerView + Adapter for the transaction list


How to Run


Open the project in Android Studio.
Let Gradle sync and download dependencies.
Run on an emulator or physical device.


Project Structure

app/src/main/java/com/seufintech/fintechapp/
├── data/        # Room entities, DAO, database
├── adapter/     # RecyclerView adapter for transactions
└── screens/     # Activities (MainActivity, CadastroActivity, ExtratoActivity)
