# JavaFX Calculator — README

## Project Overview

This is a simple desktop calculator application built using JavaFX. It
supports the four basic arithmetic operations — addition, subtraction,
multiplication, and division — along with decimal point input and a clear
function. The application is designed as a single-class JavaFX program that
demonstrates event-driven programming, layout containers, and basic arithmetic
logic.




---

## User Interface

The calculator window is 380 × 500 pixels and contains two main areas.

The display area at the top is a non-editable TextField that shows the
current number being entered or the result of a calculation. It is styled
with a large font and right-aligned text, mimicking the look of a physical
calculator display.

The button grid below the display arranges 17 buttons in a 4-column
GridPane with 5 rows. The digit buttons 1 through 9 occupy the first three
columns of the first three rows. The operator buttons — division, multiply,
subtract, and add — occupy the fourth column. The bottom row contains the
zero button, the decimal point button, the clear button, and the equals
button which spans all four columns.

---

## Supported Operations

| Button | Action |
|---|---|
| 0 – 9 | Appends the digit to the current number on the display |
| . | Appends a decimal point — only one decimal point allowed per number |
| + | Stores the current number and marks addition as the pending operation |
| - | Stores the current number and marks subtraction as the pending operation |
| * | Stores the current number and marks multiplication as the pending operation |
| / | Stores the current number and marks division as the pending operation |
| = | Completes the calculation and displays the result |
| C | Clears all state and resets the display to zero |

---

## Application Logic — How It Works Internally

### State Variables

The class maintains three state variables that drive all calculation logic.

`startNewNumber` is a boolean that controls whether the next digit press
should start a fresh number on the display or append to the existing one.
It is set to true at the beginning, after an operator is pressed, and after
equals is pressed. It is set to false as soon as the first digit of a new
number is typed.

`op` is a String that holds the currently selected operator — one of "+",
"-", "*", or "/". It is set when the user presses an operator button and
cleared after equals is pressed.

`firstNumber` is a float that stores the first operand — the number that
was on the display when the user pressed an operator button.

### Digit Input Flow

When a digit button is pressed, the handler checks `startNewNumber`. If it
is true, the display is replaced entirely with the new digit and
`startNewNumber` is set to false. If it is false, the digit is appended to
whatever is currently on the display using `appendText`.

### Operator Press Flow

When any of the four operator buttons is pressed, the current display value
is parsed and stored in `firstNumber`, the operator string is stored in
`op`, and `startNewNumber` is set to true so the next digit begins a fresh
second number.

### Equals Press Flow

When equals is pressed, the current display value is parsed as the second
number. If the operator is division and the second number is zero, the
display shows "error" and the calculator state is cleared to prevent
further invalid operations. Otherwise, the `calculate` method is called
with the second number, and the result is displayed. If the result has no
fractional part — meaning it is a whole number — it is displayed as an
integer without a decimal point. If it has a fractional part, it is
displayed as a float. After equals, `startNewNumber` is set to true.

### Decimal Point Flow

When the dot button is pressed, the handler first checks if the display
currently shows "error" or if `startNewNumber` is true — in both cases the
display is replaced with "0." to start a clean decimal number. If neither
condition is true, the handler checks whether a decimal point already exists
in the current display string. If one exists, nothing happens — a second
decimal point is not added. If none exists, the decimal point is appended.

### Clear Flow

The `clear()` helper method resets all three state variables to their
initial values — `op` becomes an empty string, `startNewNumber` becomes
true, and `firstNumber` becomes 0. The Clear button calls this method and
also resets the display to "0".

---

## Class Structure

| Member | Type | Purpose |
|---|---|---|
| `btns` | `List<Button>` | Holds the 10 digit buttons (0–9) for indexed access |
| `tf` | `TextField` | The calculator display |
| `p` | `BorderPane` | Root layout container |
| `startNewNumber` | `boolean` | Controls digit append vs replace behavior |
| `op` | `String` | Stores the pending arithmetic operator |
| `firstNumber` | `float` | Stores the first operand |
| `start(Stage)` | method | Builds the UI and registers all event handlers |
| `createBtn(String)` | method | Factory method — creates a styled Button |
| `calculate(int)` | method | Performs the arithmetic and returns the result |
| `clear()` | method | Resets all state variables |

---

## Known Limitations and Bugs

### Bug 1 — Second Operand is Parsed as Integer

In the equals handler, the second number is parsed using
`Integer.parseInt()` instead of `Float.parseFloat()`. This means that if
the user enters a decimal second number such as 3.5, the application will
throw a `NumberFormatException` and crash. The fix is to parse the second
number using `Float.parseFloat()` and update the `calculate()` method to
accept a float parameter instead of int.

### Bug 2 — No Operator Selected Before Equals

If the user presses equals without first pressing an operator button, the
variable `op` is an empty string and the switch statement in `calculate()`
matches no case, returning 0. Additionally, calling `op.equals("/")` in the
equals handler will work correctly because `op` is initialized to null only
conceptually — but if `op` is never assigned and remains uninitialized, a
NullPointerException could occur depending on the execution path. A null
or empty check on `op` before processing equals is recommended.

### Bug 3 — No Consecutive Operations

After pressing equals, the result is shown but the user cannot immediately
use that result as the first operand of a new operation by pressing another
operator button. The result is only preserved in the display — `firstNumber`
has already been reset by `clear()`. Pressing an operator after equals will
correctly read the displayed result, so this actually works, but it is not
by explicit design and should be documented or made intentional.

### Bug 4 — Negative Numbers Not Supported

There is no plus/minus toggle button. The user cannot enter a negative
number as the first operand. If a subtraction results in a negative value
the display shows it correctly, but the user cannot type a negative number
directly.

### Bug 5 — Integer Digit Buttons Use Anonymous Inner Classes

The digit buttons 0 through 9 use the verbose anonymous inner class syntax
for their event handlers, while the operator and function buttons use the
more concise lambda syntax. For consistency and readability, all handlers
should use lambdas.


---

*Programming 3 - Lab CSCI 2108 · Aya Nabil Alharazin 2026*
