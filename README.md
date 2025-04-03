The goal is to create a simple Android (Java) application that takes details of multiple payments from the user and
saves the details to a file.

Main activity
As soon as the application launches, the main activity should be displayed. This activity allows the user to enter
details payment made to someone, as shown above.
The user should be able to click the ‘Add payment’ link and add details of the payment (show above) in the dialog
box that pops up.
Up to three different types of payment (cash, bank transfer or credit card) can be added by the user. Each type of
payment can be added only once (for example, the user cannot add two cash payments).
The total amount should be updated whenever a payment is added or removed2

. The total amount cannot be
edited. A payment can be removed by pressing on the ‘x’ button next to the chip that shows the payment).
Each added payment is displayed in the main activity a delete-able chip – a chip is a standard Android Material
Design view class.
Saving the entered data
When the user clicks ‘Save’, write all the entered details (the different payment types, amounts, provider and
transaction reference, if any) to a text file called ‘LastPayment.txt’. Save the data in JSON format.
When the application starts again, the activity should load the payment details back from the file (if it exists), and
the application should behave exactly as it would have at the time the details were saved. Entered data must be
saved only when the user clicks the ‘Save’ button.
Add Payment dialog
This dialog allows the user to add a payment.
This dialog must only display in the spinner the types of payments that have not already been added before – so, if
the user has already added a Cash payment, only Bank Transfer and Credit Card must be shown in the spinner.
If Bank Transfer or Credit Card payment is chosen, two additional edit boxes shown above are displayed, where the
user can enter more details of the payment. These details must also be saved in the file when the ‘Save’ button is
pressed.
