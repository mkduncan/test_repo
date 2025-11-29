# Comprehensive CPL (Cognitive Programming Language) Command Reference Guide
## Table of Contents
1. [Header and Format Commands](#header-and-format-commands)
2. [Printing Control Commands](#printing-control-commands)
3. [Drawing and Graphics Commands](#drawing-and-graphics-commands)
4. [Barcode Commands](#barcode-commands)
5. [Text and Font Commands](#text-and-font-commands)
6. [Variable and Data Management Commands](#variable-and-data-management-commands)
7. [Printer Configuration Variables](#printer-configuration-variables)
8. [Object and Storage Commands](#object-and-storage-commands)
9. [Diagnostic and Internal Bang Commands](#diagnostic-and-internal-bang-commands)
10. [Menu and User Interface Commands](#menu-and-user-interface-commands)
11. [Network and Communication Commands](#network-and-communication-commands)
12. [Bluetooth Commands](#bluetooth-commands)
13. [Time and Date Commands](#time-and-date-commands)
14. [Query Commands](#query-commands)
---
## Header and Format Commands
Commands for defining label formats, headers, and storage/recall of formats. Sorted lexicographically.
### BACKGROUND HEADER
**Command name:** `!# n LAR Length Quantity` or `BACKGROUND HEADER`
**Alternative formats:** None
**Parameters (Inputs):**
- `n` - Head1Range (fixed value, often 0)
- `LAR` - Head2Range (length adjustment range)
- `Length` - Head3Range (label length)
- `Quantity` - Head4Range (number of labels)
**Outputs (Return values):** None (background configuration)
**Usage examples:**
```
!# 0 100 200 5
STRING 12x16 10 10 Background Label
END
```
**Description:** Sets background header for repeated printing on every label.
---
### CLEAR BACKGROUND HEADER
**Command name:** `!* n LAR Length Quantity` or `CLEAR BACKGROUND HEADER`
**Alternative formats:** None
**Parameters (Inputs):**
- `n` - Head1Range (fixed value, often 0)
- `LAR` - Head2Range (length adjustment range)
- `Length` - Head3Range (label length)
- `Quantity` - Head4Range (number of labels)
**Outputs (Return values):** None (background clearing)
**Usage examples:**
```
!* 0 100 200 1
STRING 12x16 10 10 Cleared Background
END
```
**Description:** Clears previously set background header.
---
### ENHANCED END
**Command name:** `ENHEND` or `ENHANCED END`
**Alternative formats:** None (virtual command - double-delimiter in enhanced stored format)
**Parameters (Inputs):** None
**Outputs (Return values):** Enhanced label termination
**Usage examples:**
```
! 0 100 100 1
STRING 12x16 10 10 Enhanced End
ENHEND
```
**Description:** Enhanced end command for special label processing in stored formats.
---
### END
**Command name:** `END`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Label termination
**Usage examples:**
```
! 0 100 100 1
STRING 12x16 10 10 Hello World
END
```
**Description:** Terminates a label format and initiates printing.
---
### END NO PRINT
**Command name:** `NOPRINTEND` or `END NO PRINT`
**Alternative formats:** `ENDNOPRINT`
**Parameters (Inputs):** None
**Outputs (Return values):** Label termination without printing
**Usage examples:**
```
! 0 100 100 1
STRING 12x16 10 10 Not Printed
NOPRINTEND
```
**Description:** Terminates label format without initiating printing.
---
### FOREGROUND GRAPHIC MODE
**Command name:** `@ header line` or `FOREGROUND GRAPHIC MODE`
**Alternative formats:** None (implicit in header)
**Parameters (Inputs):**
- Header parameters (e.g., `0 LAR LEN Quantity`)
**Outputs (Return values):** Foreground graphic mode setting
**Usage examples:**
```
!@ 0 100 200 1
[foreground graphic commands]
END
```
**Description:** Sets foreground graphic mode for header line rendering.
---
### HEADER
**Command name:** `! n LAR Length Quantity` or `HEADER`
**Alternative formats:** Standard header line only
**Parameters (Inputs):**
- `n` - Head1Range (fixed value, often 0)
- `LAR` - Head2Range (length adjustment range)
- `Length` - Head3Range (label length)
- `Quantity` - Head4Range (number of labels)
**Outputs (Return values):** None (configuration command)
**Usage examples:**
```
! 0 100 100 0
String 18x23 10 50 Hello World!
END
```
**Description:** Defines the header parameters for a label format, including number of labels, length adjustment, label length, and quantity.
---
### HEADER AUTO
**Command name:** `!A` or `HEADER AUTO`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (configuration command)
**Usage examples:**
```
!A
STRING 12x16 10 10 Auto Header Test
END
```
**Description:** Automatic header configuration that calculates optimal parameters.
---
### HEADER PLUS
**Command name:** `!+ 0 LAR len quantity` or `HEADER PLUS`
**Alternative formats:** None
**Parameters (Inputs):**
- `0` - Fixed value
- `LAR` - Length adjustment range
- `len` - Label length
- `quantity` - Number of labels
**Outputs (Return values):** None (configuration command)
**Usage examples:**
```
!+ 0 100 200 5
STRING 12x16 10 10 Test Label
END
```
**Description:** Extended header command with additional parameters for label formatting.
---
### PASSWORD HEADER
**Command name:** `!Pxxy 0 LAR LEN Quantity` or `PASSWORD HEADER`
**Alternative formats:** None
**Parameters (Inputs):**
- `xxy` - Password digits (e.g., 123)
- `0` - Fixed value
- `LAR` - Head2Range (length adjustment range)
- `LEN` - Head3Range (label length)
- `Quantity` - Head4Range (number of labels)
**Outputs (Return values):** None (protected header)
**Usage examples:**
```
!P123 0 100 200 1
STRING 12x16 10 10 Protected Label
END
```
**Description:** Sets password-protected header for secure label formats.
---
### RECALL FORMAT
**Command name:** `RECALL FORMAT formatname [variable data]` or `RECALL FORMAT`
**Alternative formats:** None
**Parameters (Inputs):**
- `formatname` - Name of stored format
- `[variable data]` - Data for variable fields (optional)
**Outputs (Return values):** None (format execution)
**Usage examples:**
```
RECALL FORMAT LABEL1
END
```
```
RECALL FORMAT LABEL2 "Product Name","BARCODE123"
END
```
**Description:** Recalls and executes stored label format, optionally supplying variable data for enhanced formats.
---
### STORE ENHANCED FORMAT
**Command name:** `STORE ENHANCED FORMAT formatname` or `STORE ENHANCED FORMAT`
**Alternative formats:** None
**Parameters (Inputs):**
- `formatname` - Unique format identifier
- `[enhanced format data follows]` - Complete label format with variables
**Outputs (Return values):** Storage confirmation
**Usage examples:**
```
STORE ENHANCED FORMAT LABEL2
! 0 100 200 1
STRING 12x16 10 10 $FIELD1$
BARCODE CODE39 10 40 30 $FIELD2$
END
```
**Description:** Stores enhanced label format with variable field placeholders.
---
### STORE FORMAT
**Command name:** `STORE FORMAT formatname` or `STORE FORMAT`
**Alternative formats:** None
**Parameters (Inputs):**
- `formatname` - Unique format identifier
- `[format data follows]` - Complete label format definition
**Outputs (Return values):** Storage confirmation
**Usage examples:**
```
STORE FORMAT LABEL1
! 0 100 200 1
STRING 12x16 10 10 Stored Format
BARCODE CODE39 10 40 30 STOREDLBL
END
```
**Description:** Stores complete label format in memory for later recall.
---
## Printing Control Commands
Commands for controlling print flow, pauses, quantities, and adjustments. Sorted lexicographically.
### ADJUST
**Command name:** `ADJUST nnn` or `A nnn`
**Alternative formats:** `ADJUST ID nnn` or `A ID nnn`
**Parameters (Inputs):**
- `ID` (optional) - Variable identifier (IDSize)
- `nnn` - Adjustment value (IntRange, can be positive or negative)
**Outputs (Return values):** None (modification command)
**Usage examples:**
```
! 0 100 200 3
BARCODE CODE39 150 30 30 TEST20
ADJUST -01
STRING 12X16 150 65 ADJUST20
ADJUST 01
END
```
**Description:** Increments or decrements a variable value or numeric data on the preceding command line. Used for serial numbering and data progression.
---
### ADJUST DUPLICATE
**Command name:** `ADJUST_DUP nnn` or `AP nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Number of duplicate labels (UIntRange)
**Outputs (Return values):** None (print control command)
**Usage examples:**
```
! 0 100 50 2
STRING 8X8 0 0 1000
ADJUST 0001
ADJUST_DUP 2
END
```
**Description:** Prints non-incremented duplicates of incremented labels when used with ADJUST command.
---
### AREA CLEAR
**Command name:** `AREA_CLEAR x y w h` or `AR x y w h`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
**Outputs (Return values):** None (drawing command)
**Usage examples:**
```
AREA_CLEAR 50 50 200 100
STRING 12x16 60 60 Cleared Area Text
END
```
**Description:** Clears a rectangular area of a label for replotting.
---
### BACKGROUND GRAPHIC MODE
**Command name:** `# header line` or `BACKGROUND GRAPHIC MODE`
**Alternative formats:** None (implicit in background header)
**Parameters (Inputs):** Background header parameters
**Outputs (Return values):** Background graphic mode setting
**Usage examples:**
```
!# 0 100 200 5
[graphic commands for background]
END
```
**Description:** Sets background graphic mode for header line rendering.
---
### DELIMIT
**Command name:** `DELIMIT character` or `D character`
**Alternative formats:** None
**Parameters (Inputs):**
- `character` - Delimiter character
**Outputs (Return values):** None (format control)
**Usage examples:**
```
DELIMIT ,
STRING 12x16 10 10 Field1,Field2,Field3
END
```
**Description:** Sets field delimiter character for data parsing.
---
### HALT
**Command name:** `HALT`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Print pause
**Usage examples:**
```
! 0 100 100 3
STRING 12x16 10 10 Label 1
HALT
STRING 12x16 10 30 Label 2
END
```
**Description:** Pauses printing after current label, waiting for operator intervention.
---
### INDEX
**Command name:** `INDEX` or `I`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (index control)
**Usage examples:**
```
INDEX
STRING 12x16 10 10 Indexed Label
END
```
**Description:** Enables label indexing for this label.
---
### JUSTIFY
**Command name:** `JUSTIFY where` or `J where`
**Alternative formats:** None
**Parameters (Inputs):**
- `where` - Justification: LEFT, CENTER, RIGHT, L, C, or R
**Outputs (Return values):** None (text alignment)
**Usage examples:**
```
JUSTIFY CENTER
STRING 12x16 10 10 Centered Text
END
```
**Description:** Sets text justification for subsequent text commands.
---
### MULTIPLE
**Command name:** `MULTIPLE nnn` or `M nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Multiple factor (MultipleRange: 2-9)
**Outputs (Return values):** None (print control)
**Usage examples:**
```
MULTIPLE 2
STRING 12x16 10 10 Double Size
END
```
**Description:** Multiplies subsequent dimensions by specified factor.
---
### NOINDEX
**Command name:** `NOINDEX` or `N`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (index control)
**Usage examples:**
```
NOINDEX
STRING 12x16 10 10 Non-Indexed Label
END
```
**Description:** Disables label indexing for this label.
---
### OFFSET
**Command name:** `OFFSET ...`
**Alternative formats:** None
**Parameters (Inputs):** Various (e.g., `10 20` for x,y offsets)
**Outputs (Return values):** None (positioning)
**Usage examples:**
```
OFFSET 10 20
STRING 12x16 0 0 Offset Text
END
```
**Description:** Sets coordinate offset for subsequent commands (not supported in all models).
---
### PITCH
**Command name:** `PITCH pitch` or `P pitch`
**Alternative formats:** `VARIABLE PITCH pitch`
**Parameters (Inputs):**
- `pitch` - Print pitch setting (enumPitch: 100/200 for 203 DPI, 150/300 for 300 DPI)
**Outputs (Return values):** None (print density setting)
**Usage examples:**
```
PITCH 200
STRING 12x16 10 10 200 DPI Text
END
```
**Description:** Sets the print pitch/density for the printhead.
---
### PROMPTS
**Command name:** `PROMPTS ON/OFF/?`
**Alternative formats:** None
**Parameters (Inputs):**
- `ON/OFF/?` - Prompt control setting
**Outputs (Return values):** Current prompt status if queried
**Usage examples:**
```
PROMPTS ON
STRING 12x16 10 10 Enter Data:
END
```
**Description:** Controls user prompting during label printing.
---
### QUANTITY
**Command name:** `QUANTITY nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Number of labels to print
**Outputs (Return values):** None (print control)
**Usage examples:**
```
! 0 100 100 1
STRING 12x16 10 10 Test Label
QUANTITY 10
END
```
**Description:** Specifies the number of labels to print.
---
### WIDTH
**Command name:** `WIDTH width` or `W width`
**Alternative formats:** `VARIABLE WIDTH width`
**Parameters (Inputs):**
- `width` - Label width in user units (WidthRange: 0-65535)
**Outputs (Return values):** None (width setting)
**Usage examples:**
```
WIDTH 400
STRING 12x16 10 10 Wide Label
END
```
**Description:** Sets the label width for formatting purposes.
---
## Drawing and Graphics Commands
Commands for shapes, fills, symbols, and graphic rendering/storage. Sorted lexicographically.
### DRAW BOX
**Command name:** `DRAW_BOX x y w h` or `BOX x y w h`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
**Outputs (Return values):** None (drawing command)
**Usage examples:**
```
DRAW_BOX 10 10 100 50
STRING 12x16 20 25 Box Text
END
```
**Description:** Draws a rectangular box outline.
---
### DRAW CIRCLE
**Command name:** `DRAW_CIRCLE x y radius` or `CIRCLE x y radius`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - Center X coordinate (BoxRange)
- `y` - Center Y coordinate (BoxRange)
- `radius` - Circle radius (BoxRange)
**Outputs (Return values):** None (drawing command)
**Usage examples:**
```
DRAW_CIRCLE 50 50 25
STRING 12x16 35 45 Circle
END
```
**Description:** Draws a circle outline.
---
### DRAW ELLIPSE
**Command name:** `DRAW_ELLIPSE x y w h` or `ELLIPSE x y w h`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
**Outputs (Return values):** None (drawing command)
**Usage examples:**
```
DRAW_ELLIPSE 10 10 80 40
STRING 12x16 30 25 Ellipse
END
```
**Description:** Draws an ellipse outline.
---
### DRAW LINE
**Command name:** `DRAW_LINE x1 y1 x2 y2` or `LINE x1 y1 x2 y2`
**Alternative formats:** Extended: `DRAW_LINE x y x2 y2 [t] [c]` or `DL x y x2 y2 [t] [c]`
**Parameters (Inputs):**
- `x1` - Start X coordinate (BoxRange)
- `y1` - Start Y coordinate (BoxRange)
- `x2` - End X coordinate (BoxRange)
- `y2` - End Y coordinate (BoxRange)
- `t` (optional) - Line thickness (ui32000_range)
- `c` (optional) - Color (enumColorValue: B or W)
**Outputs (Return values):** None (drawing command)
**Usage examples:**
```
DRAW_LINE 10 10 100 50
STRING 12x16 20 30 Line Text
END
```
```
DRAW_LINE 10 10 100 50 2 B
STRING 12x16 20 30 Thick Black Line
END
```
**Description:** Draws a straight line between two points, with optional thickness and color in extended mode.
---
### DRAW SYMBOL
**Command name:** `DRAW_SYMBOL x y symbol` or `SYMBOL x y symbol`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `symbol` - Symbol identifier (enumSymValue: A, B, C, D, E)
**Outputs (Return values):** None (symbol rendering)
**Usage examples:**
```
DRAW_SYMBOL 10 10 A
STRING 12x16 30 10 Symbol Text
END
```
**Description:** Draws a predefined symbol at specified coordinates.
---
### FILL BOX
**Command name:** `FILL_BOX x y w h` or `FBOX x y w h`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
**Outputs (Return values):** None (drawing command)
**Usage examples:**
```
FILL_BOX 10 10 100 50
STRING 12x16 20 25 Filled Box
END
```
**Description:** Draws a filled rectangular box.
---
### GRAPHIC
**Command name:** `GRAPHIC x y data` or `G x y data`
**Alternative formats:** Extended: `GRAPHIC x y width height data`
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `width` (extended) - Image width in dots
- `height` (extended) - Image height in dots
- `data` - Graphic data (raw hex/binary)
**Outputs (Return values):** None (graphic rendering)
**Usage examples:**
```
GRAPHIC 10 10 graphic_data
END
```
```
! 0 100 200 1
GRAPHIC 10 10 100 50 [binary data]
STRING 12x16 10 70 Direct Graphic
END
```
**Description:** Renders graphic data at specified coordinates, with extended dimensions.
---
### GRAPHIC RECALL
**Command name:** `!R G ID` or `GRAPHIC RECALL graphicname x y`
**Alternative formats:** `RECALL_GRAPHIC ID`
**Parameters (Inputs):**
- `ID` or `graphicname` - Graphic identifier
- `x` (extended) - X coordinate for placement
- `y` (extended) - Y coordinate for placement
**Outputs (Return values):** None (graphic recall)
**Usage examples:**
```
!R G LOGO
END
```
```
! 0 100 200 1
GRAPHIC RECALL LOGO1 10 10
STRING 12x16 10 120 Company Name
END
```
**Description:** Recalls a stored graphic for printing at specified position.
---
### GRAPHIC STORE
**Command name:** `!Sx storage Cm<crlf>graphic_data_file` or `GRAPHIC STORE graphicname width height data`
**Alternative formats:** `GRAPHIC_STORE`
**Parameters (Inputs):**
- `x` - Storage parameters
- `storage` - Storage location
- `graphicname` (extended) - Unique graphic identifier
- `width` (extended) - Image width in dots
- `height` (extended) - Image height in dots
- `graphic_data_file` or `data` - Graphic data file/raw data
**Outputs (Return values):** None (graphic storage)
**Usage examples:**
```
!S1 3 Cm
[graphic data]
END
```
```
GRAPHIC STORE LOGO1 200 100 [binary data]
END
```
**Description:** Stores graphic data in printer memory for later recall.
---
### RECALL GRAPHIC
**Command name:** `RECALL GRAPHIC ID` or `GRAPHIC RECALL`
**Alternative formats:** See GRAPHIC RECALL
**Parameters (Inputs):** See GRAPHIC RECALL
**Outputs (Return values):** See GRAPHIC RECALL
**Usage examples:** See GRAPHIC RECALL
**Description:** Alias for recalling stored graphics.
---
### STORE GRAPHIC
**Command name:** `STORE GRAPHIC`
**Alternative formats:** See GRAPHIC STORE
**Parameters (Inputs):** See GRAPHIC STORE
**Outputs (Return values):** See GRAPHIC STORE
**Usage examples:** See GRAPHIC STORE
**Description:** Alias for storing graphics.
---
## Barcode Commands
Commands for generating 1D and 2D barcodes. Sorted lexicographically.
### BARCODE
**Command name:** `BARCODE type x y w h data` or `B type x y w h data`
**Alternative formats:** Various barcode-specific formats
**Parameters (Inputs):**
- `type` - Barcode type (CODE39, CODE128, EAN13, etc.)
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
- `data` - Barcode data string
**Outputs (Return values):** None (barcode generation)
**Usage examples:**
```
BARCODE CODE39 10 10 100 30 ABC123
STRING 12x16 10 45 Barcode Text
END
```
**Description:** Generates various types of 1D barcodes.
---
### BARCODE AZTEC
**Command name:** `BARCODE_AZTEC x y size data` or `AZTEC x y size data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `size` - Aztec code size (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (2D barcode generation)
**Usage examples:**
```
BARCODE_AZTEC 10 10 80 AZTEC123
STRING 12x16 100 30 Aztec Code
END
```
**Description:** Generates Aztec 2D barcodes.
---
### BARCODE DATAMATRIX
**Command name:** `BARCODE_DATAMATRIX x y size data` or `DM x y size data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `size` - DataMatrix size (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (2D barcode generation)
**Usage examples:**
```
BARCODE_DATAMATRIX 10 10 80 ABC123
STRING 12x16 100 30 DataMatrix
END
```
**Description:** Generates DataMatrix 2D barcodes.
---
### BARCODE FONT (String)
**Command name:** `BARCODE_FONT_STRING fontID(spacing,rotation,xmult,ymult) x y data` or `BSTRING fontID(spacing,rotation,xmult,ymult) x y data`
**Alternative formats:** None
**Parameters (Inputs):**
- `fontID` - Font identifier
- `spacing` - Character spacing
- `rotation` - Rotation angle
- `xmult` - X multiplier
- `ymult` - Y multiplier
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (barcode generation)
**Usage examples:**
```
BARCODE_FONT_STRING 3(0,0,1,1) 10 10 CODE128DATA
END
```
**Description:** Generates barcode using string font format.
---
### BARCODE FONT (Text)
**Command name:** `BARCODE_FONT_TEXT fontID(spacing,rotation,xmult,ymult) x y data` or `BTEXT fontID(spacing,rotation,xmult,ymult) x y data`
**Alternative formats:** None
**Parameters (Inputs):**
- `fontID` - Font identifier
- `spacing` - Character spacing
- `rotation` - Rotation angle
- `xmult` - X multiplier
- `ymult` - Y multiplier
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (barcode generation)
**Usage examples:**
```
BARCODE_FONT_TEXT 3(0,0,1,1) 10 10 CODE128DATA
END
```
**Description:** Generates barcode using text font format.
---
### BARCODE FONT (Ultra)
**Command name:** `BARCODE_FONT_ULTRA fontID(spacing,rotation,xmult,ymult) x y data` or `BULTRA fontID(spacing,rotation,xmult,ymult) x y data`
**Alternative formats:** None
**Parameters (Inputs):**
- `fontID` - Font identifier
- `spacing` - Character spacing
- `rotation` - Rotation angle
- `xmult` - X multiplier
- `ymult` - Y multiplier
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (barcode generation)
**Usage examples:**
```
BARCODE_FONT_ULTRA 3(0,0,1,1) 10 10 CODE128DATA
END
```
**Description:** Generates barcode using ultra font format.
---
### BARCODE MICRO PDF417
**Command name:** `BARCODE_MICROPDF x y w h data` or `MICROPDF x y w h data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (2D barcode generation)
**Usage examples:**
```
BARCODE_MICROPDF 10 10 100 50 MICRODATA
STRING 12x16 10 65 Micro PDF417
END
```
**Description:** Generates Micro PDF417 2D barcodes.
---
### BARCODE PDF417
**Command name:** `BARCODE_PDF417 x y w h data` or `PDF417 x y w h data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (2D barcode generation)
**Usage examples:**
```
BARCODE_PDF417 10 10 100 50 Sample PDF417 Data
STRING 12x16 10 65 PDF417 Barcode
END
```
**Description:** Generates PDF417 2D barcodes.
---
### BARCODE QR
**Command name:** `BARCODE_QR x y size data` or `QR x y size data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `size` - QR code size (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (2D barcode generation)
**Usage examples:**
```
BARCODE_QR 10 10 100 https://example.com
STRING 12x16 120 30 QR Code
END
```
**Description:** Generates QR code 2D barcodes.
---
### BARCODE RSS
**Command name:** `BARCODE_RSS x y w h data` or `RSS x y w h data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (2D barcode generation)
**Usage examples:**
```
BARCODE_RSS 10 10 100 50 RSS123456789
STRING 12x16 10 65 RSS Barcode
END
```
**Description:** Generates RSS (Reduced Space Symbology) barcodes.
---
### BARCODE UPS
**Command name:** `BARCODE_UPS x y w h data` or `UPS x y w h data`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `w` - Width (BoxRange)
- `h` - Height (BoxRange)
- `data` - Data to encode
**Outputs (Return values):** None (1D barcode generation)
**Usage examples:**
```
BARCODE_UPS 10 10 100 50 1Z12345678
STRING 12x16 10 65 UPS Barcode
END
```
**Description:** Generates UPS MaxiCode barcodes for shipping.
---
## Text and Font Commands
Commands for rendering text, fonts, and international characters. Sorted lexicographically.
### COMMENT
**Command name:** `COMMENT text` or `; text`
**Alternative formats:** `C text`
**Parameters (Inputs):**
- `text` - Comment text
**Outputs (Return values):** None (documentation)
**Usage examples:**
```
COMMENT This is a comment
STRING 12x16 10 10 Test
END
```
**Description:** Adds comments to label format for documentation purposes.
---
### DATASKIP
**Command name:** `DATASKIP count` or `DATASKIP UNTIL char [repeat]`
**Alternative formats:** None
**Parameters (Inputs):**
- `count` - Number of characters to skip (UIntRange)
- `UNTIL` - Skip until character found
- `char` - Target character to find
- `repeat` (optional) - Number of repetitions (UIntRange)
**Outputs (Return values):** None (data parsing control)
**Usage examples:**
```
DATASKIP 5
DATASKIP UNTIL , 2
STRING 12x16 10 10 Processed Data
END
```
**Description:** Skips specified number of characters or skips until specified character.
---
### DEFINE VARIABLE
**Command name:** `DEFINE_VAR name value` or `DV name value`
**Alternative formats:** None
**Parameters (Inputs):**
- `name` - Variable name (IDSize)
- `value` - Initial value
**Outputs (Return values):** None (variable definition)
**Usage examples:**
```
DEFINE_VAR COUNTER 001
STRING 12x16 10 10 COUNTER: COUNTER
ADJUST COUNTER 01
END
```
**Description:** Defines a variable that can be used throughout the label format.
---
### DOUBLE FONT
**Command name:** `DOUBLE font(exspace,rotation,xmult,ymult) x y mtid characters` or `DOUBLE`
**Alternative formats:** None
**Parameters (Inputs):**
- `font` - Font identifier
- `exspace` - Extra spacing
- `rotation` - Rotation angle
- `xmult` - X multiplier
- `ymult` - Y multiplier
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `mtid` - Multi-byte text identifier
- `characters` - Double-byte characters
**Outputs (Return values):** None (double-byte text rendering)
**Usage examples:**
```
DOUBLE 3(1,0,2,2) 10 10 1234 漢字
STRING 12x16 10 40 Double-byte Text
END
```
**Description:** Renders double-byte character text (e.g., Chinese, Japanese) with formatting.
---
### RECALL VARIABLE
**Command name:** `!R V ID [HIDE]` or `RECALL_VAR ID [HIDE]`
**Alternative formats:** None
**Parameters (Inputs):**
- `ID` - Variable identifier (IDSize)
- `HIDE` (optional) - Hide parameter (Hideptr)
**Outputs (Return values):** Variable value
**Usage examples:**
```
!R V COUNTER
STRING 12x16 10 10 Recalled: COUNTER
END
```
**Description:** Recalls a stored variable value for use in the current label.
---
### STRING
**Command name:** `STRING font x y text` or `S font x y text`
**Alternative formats:** None
**Parameters (Inputs):**
- `font` - Font specification (e.g., 12x16)
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `text` - Text string to print
**Outputs (Return values):** None (text rendering)
**Usage examples:**
```
STRING 12x16 10 10 Hello World
STRING 8x8 10 30 Small Text
END
```
**Description:** Prints text using specified font and coordinates.
---
### TEXT
**Command name:** `TEXT font x y text` or `T font x y text`
**Alternative formats:** None
**Parameters (Inputs):**
- `font` - Font specification
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `text` - Text string to print
**Outputs (Return values):** None (text rendering)
**Usage examples:**
```
TEXT 12x16 10 10 Text Command
END
```
**Description:** Alternative text command with similar functionality to STRING.
---
### THAI FONT
**Command name:** `THAI fontID(spacing,rotation,xmult,ymult) x y characters` or `THAI`
**Alternative formats:** None
**Parameters (Inputs):**
- `fontID` - Font identifier
- `spacing` - Character spacing
- `rotation` - Rotation angle
- `xmult` - X multiplier
- `ymult` - Y multiplier
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `characters` - Thai text characters
**Outputs (Return values):** None (Thai text rendering)
**Usage examples:**
```
THAI 3(0,0,1,1) 10 10 สวัสดี
STRING 12x16 10 30 Thai Text Above
END
```
**Description:** Renders Thai text with specified font and formatting parameters.
---
### ULTRA FONT
**Command name:** `ULTRA_FONT TnnnXmmm IGz(Boldness,ExtraSpace,Rotation) x y characters` or `U TnnnXmmm IGz(...) x y characters`
**Alternative formats:** None
**Parameters (Inputs):**
- `TnnnXmmm` - Font size specification (Tnnn = horizontal, Xmmm = vertical)
- `IGz` - Format parameters: Boldness (level), ExtraSpace (spacing), Rotation (angle)
- `x` - X coordinate (BoxRange)
- `y` - Y coordinate (BoxRange)
- `characters` - Text to render
**Outputs (Return values):** None (vector font rendering)
**Usage examples:**
```
ULTRA_FONT T10X15 IGz(2,1,0) 10 10 Ultra Text
STRING 12x16 10 40 Standard Text
END
```
**Description:** Renders text using ultra vector font with advanced formatting options.
---
### VARIABLE (General)
**Command name:** `VARIABLE parameter value` or `V parameter value`
**Alternative formats:** Various specific VARIABLE commands
**Parameters (Inputs):**
- `parameter` - Specific variable parameter (e.g., ERROR_LEVEL, MODE, PITCH)
- `value` - Parameter value
**Outputs (Return values):** None (printer configuration)
**Usage examples:**
```
VARIABLE ERROR_LEVEL 1
VARIABLE MODE 3
VARIABLE WRITE
END
```
**Description:** Sets various printer configuration parameters.
---
## Variable and Data Management Commands
Commands for variables, data skipping, and limits. Sorted lexicographically.
### DUMP VARIABLES
**Command name:** `!DUMP VariablesType` or `DUMP VARIABLES`
**Alternative formats:** None
**Parameters (Inputs):**
- `VariablesType` - Variable type: USERVARS, FACTORYAVARS, or FACTORYBVARS
**Outputs (Return values):** Contents of specified variable area
**Usage examples:**
```
!DUMP USERVARS
!DUMP FACTORYAVARS
!DUMP FACTORYBVARS
END
```
**Description:** Dumps the selected copy of the Printer Variables object for debugging.
---
## Printer Configuration Variables
All VARIABLE subcommands for printer settings. Sorted lexicographically by full command name.
### VARIABLE ALLOCATE
**Command name:** `VARIABLE ALLOCATE size` or `V ALLOCATE size`
**Alternative formats:** None
**Parameters (Inputs):**
- `size` - Memory allocation size (UIntRange)
**Outputs (Return values):** None (memory management)
**Usage examples:**
```
VARIABLE ALLOCATE 1024
VARIABLE WRITE
END
```
**Description:** Allocates memory for printer operations and data storage.
---
### VARIABLE AUDIO_FREQ
**Command name:** `VARIABLE AUDIO_FREQ freq` or `V AUDIO_FREQ freq`
**Alternative formats:** None
**Parameters (Inputs):**
- `freq` - Audio frequency in Hz (UIntRange)
**Outputs (Return values):** None (audio setting)
**Usage examples:**
```
VARIABLE AUDIO_FREQ 2000
VARIABLE WRITE
END
```
**Description:** Sets the frequency for printer beeper audio feedback.
---
### VARIABLE AUTOCUT
**Command name:** `VARIABLE AUTOCUT on/off/?` or `V AUTOCUT on/off/?`
**Alternative formats:** None
**Parameters (Inputs):**
- `on/off/?` - Autocut setting or query (OnOffStat)
**Outputs (Return values):** Current autocut status if queried
**Usage examples:**
```
VARIABLE AUTOCUT ON
VARIABLE WRITE
END
```
**Description:** Enables or disables automatic label cutting functionality.
---
### VARIABLE AUTO_TOF
**Command name:** `VARIABLE AUTO_TOF mode` or `V AUTO_TOF mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Automatic top-of-form setting (ON/OFF)
**Outputs (Return values):** None (media detection)
**Usage examples:**
```
VARIABLE AUTO_TOF ON
VARIABLE WRITE
END
```
**Description:** Enables automatic top-of-form detection during media loading.
---
### VARIABLE AUX_POWER
**Command name:** `VARIABLE AUX_POWER mode` or `V AUX_POWER mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Auxiliary power control (ON/OFF)
**Outputs (Return values):** None (power management)
**Usage examples:**
```
VARIABLE AUX_POWER ON
VARIABLE WRITE
END
```
**Description:** Controls auxiliary power output for external devices.
---
### VARIABLE BACKLIGHT
**Command name:** `VARIABLE BACKLIGHT level` or `V BACKLIGHT level`
**Alternative formats:** None
**Parameters (Inputs):**
- `level` - Backlight intensity level (0-100)
**Outputs (Return values):** None (display setting)
**Usage examples:**
```
VARIABLE BACKLIGHT 75
VARIABLE WRITE
END
```
**Description:** Adjusts LCD display backlight intensity.
---
### VARIABLE BEEPER
**Command name:** `VARIABLE BEEPER on/off/? [volume] [duration]` or `V BEEPER on/off/? [volume] [duration]`
**Alternative formats:** `V BEEPER mode` (extended)
**Parameters (Inputs):**
- `on/off/?` - Beeper control setting (OnOffStat)
- `volume` (optional) - Beeper volume (BeeperVolumeRange: 0-3)
- `duration` (optional) - Duration in 10ths of second (BeeperDurationRange: 0-255)
- `mode` (extended) - Beeper control (ON/OFF/VOLUME)
**Outputs (Return values):** Current beeper status if queried
**Usage examples:**
```
VARIABLE BEEPER ON 2 50
VARIABLE WRITE
END
```
```
VARIABLE BEEPER ON
VARIABLE WRITE
END
```
**Description:** Controls beeper volume and duration settings.
---
### VARIABLE BLUETOOTH BDADDR
**Command name:** `VARIABLE BLUETOOTH BDADDR [address]` or `V BLUETOOTH BDADDR [address]`
**Alternative formats:** None
**Parameters (Inputs):**
- `address` - Bluetooth device address (optional, query if omitted)
**Outputs (Return values):** Current BD address if queried
**Usage examples:**
```
VARIABLE BLUETOOTH BDADDR
END
```
**Description:** Queries or sets the Bluetooth device address (BD_ADDR).
---
### VARIABLE BLUETOOTH CLASS
**Command name:** `VARIABLE BLUETOOTH CLASS code` or `V BLUETOOTH CLASS code`
**Alternative formats:** None
**Parameters (Inputs):**
- `code` - Device class code (hexadecimal)
**Outputs (Return values):** None (device classification)
**Usage examples:**
```
VARIABLE BLUETOOTH CLASS 0x180204
VARIABLE WRITE
END
```
**Description:** Sets the Bluetooth device class identification.
---
### VARIABLE BLUETOOTH CONFIGURE
**Command name:** `VARIABLE BLUETOOTH CONFIGURE params` or `V BLUETOOTH CONFIGURE params`
**Alternative formats:** None
**Parameters (Inputs):**
- `params` - Complete Bluetooth configuration parameters (e.g., AUTO)
**Outputs (Return values):** Configuration result
**Usage examples:**
```
VARIABLE BLUETOOTH CONFIGURE AUTO
VARIABLE WRITE
END
```
**Description:** Configures comprehensive Bluetooth module settings.
---
### VARIABLE BLUETOOTH DEFAULT
**Command name:** `VARIABLE BLUETOOTH DEFAULT` or `V BLUETOOTH DEFAULT`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Default restore confirmation
**Usage examples:**
```
VARIABLE BLUETOOTH DEFAULT
END
```
**Description:** Restores Bluetooth settings to factory defaults.
---
### VARIABLE BLUETOOTH DEVICENAME
**Command name:** `VARIABLE BLUETOOTH DEVICENAME "name"` or `V BLUETOOTH DEVICENAME "name"`
**Alternative formats:** None
**Parameters (Inputs):**
- `"name"` - Bluetooth device name (up to 32 characters)
**Outputs (Return values):** None (device identification)
**Usage examples:**
```
VARIABLE BLUETOOTH DEVICENAME "Printer_001"
VARIABLE WRITE
END
```
**Description:** Sets the Bluetooth discoverable device name.
---
### VARIABLE BLUETOOTH DEVICEPIN
**Command name:** `VARIABLE BLUETOOTH DEVICEPIN "pin"` or `V BLUETOOTH DEVICEPIN "pin"`
**Alternative formats:** None
**Parameters (Inputs):**
- `"pin"` - Bluetooth pairing PIN code (4-16 digits)
**Outputs (Return values):** None (security setting)
**Usage examples:**
```
VARIABLE BLUETOOTH DEVICEPIN "1234"
VARIABLE WRITE
END
```
**Description:** Sets the Bluetooth pairing PIN code for security.
---
### VARIABLE BLUETOOTH DIAGNOSTIC
**Command name:** `VARIABLE BLUETOOTH DIAGNOSTIC` or `V BLUETOOTH DIAGNOSTIC`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Bluetooth diagnostic information
**Usage examples:**
```
VARIABLE BLUETOOTH DIAGNOSTIC
END
```
**Description:** Runs comprehensive Bluetooth module diagnostics.
---
### VARIABLE BLUETOOTH DISCOVERABLE
**Command name:** `VARIABLE BLUETOOTH DISCOVERABLE mode` or `V BLUETOOTH DISCOVERABLE mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Discoverability mode (ON/OFF/LIMITED)
**Outputs (Return values):** None (visibility setting)
**Usage examples:**
```
VARIABLE BLUETOOTH DISCOVERABLE ON
VARIABLE WRITE
END
```
**Description:** Controls Bluetooth device discoverability by other devices.
---
### VARIABLE BLUETOOTH ENCRYPTION
**Command name:** `VARIABLE BLUETOOTH ENCRYPTION mode` or `V BLUETOOTH ENCRYPTION mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Encryption enable/disable
**Outputs (Return values):** None (security setting)
**Usage examples:**
```
VARIABLE BLUETOOTH ENCRYPTION ON
VARIABLE WRITE
END
```
**Description:** Enables or disables Bluetooth connection encryption.
---
### VARIABLE BLUETOOTH FORGET
**Command name:** `VARIABLE BLUETOOTH FORGET [address]` or `V BLUETOOTH FORGET [address]`
**Alternative formats:** None
**Parameters (Inputs):**
- `address` - Bluetooth device address to forget (optional, defaults to all)
**Outputs (Return values):** Forget operation result
**Usage examples:**
```
VARIABLE BLUETOOTH FORGET
VARIABLE WRITE
END
```
**Description:** Removes paired device(s) from memory.
---
### VARIABLE BLUETOOTH RESET
**Command name:** `VARIABLE BLUETOOTH RESET` or `V BLUETOOTH RESET`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Reset confirmation
**Usage examples:**
```
VARIABLE BLUETOOTH RESET
END
```
**Description:** Resets Bluetooth module to factory defaults.
---
### VARIABLE BLUETOOTH SECURITY
**Command name:** `VARIABLE BLUETOOTH SECURITY level` or `V BLUETOOTH SECURITY level`
**Alternative formats:** None
**Parameters (Inputs):**
- `level` - Security level (0-3): 0=No security, 1=Service level, 2=Link level, 3=Service and link level
**Outputs (Return values):** None (security setting)
**Usage examples:**
```
VARIABLE BLUETOOTH SECURITY 2
VARIABLE WRITE
END
```
**Description:** Sets the Bluetooth security level.
---
### VARIABLE BUFFER_TIMED_RESET
**Command name:** `VARIABLE BUFFER_TIMED_RESET seconds` or `V BUFFER_TIMED_RESET seconds`
**Alternative formats:** None
**Parameters (Inputs):**
- `seconds` - Reset timeout in seconds (UIntRange)
**Outputs (Return values):** None (buffer management)
**Usage examples:**
```
VARIABLE BUFFER_TIMED_RESET 30
VARIABLE WRITE
END
```
**Description:** Sets automatic buffer reset timeout after idle period.
---
### VARIABLE CODE PAGE
**Command name:** `VARIABLE CODE_PAGE mode` or `V CODE_PAGE mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Code page setting (e.g., 850)
**Outputs (Return values):** None (character encoding)
**Usage examples:**
```
VARIABLE CODE_PAGE 850
VARIABLE WRITE
END
```
**Description:** Sets character code page for international text support.
---
### VARIABLE COMM
**Command name:** `VARIABLE COMM settings` or `V COMM settings`
**Alternative formats:** None
**Parameters (Inputs):**
- `settings` - Communication port settings (baud, parity, data bits, stop bits, e.g., 9600,N,8,1)
**Outputs (Return values):** None (serial configuration)
**Usage examples:**
```
VARIABLE COMM 9600,N,8,1
VARIABLE WRITE
END
```
**Description:** Configures serial communication parameters.
---
### VARIABLE COMPATIBLE
**Command name:** `VARIABLE COMPATIBLE type` or `V COMPATIBLE type`
**Alternative formats:** None
**Parameters (Inputs):**
- `type` - Compatibility mode (e.g., ZPL)
**Outputs (Return values):** None (emulation setting)
**Usage examples:**
```
VARIABLE COMPATIBLE ZPL
VARIABLE WRITE
END
```
**Description:** Sets printer compatibility mode for different command languages.
---
### VARIABLE CONTRAST
**Command name:** `VARIABLE CONTRAST level` or `V CONTRAST level`
**Alternative formats:** None
**Parameters (Inputs):**
- `level` - LCD contrast level (0-100)
**Outputs (Return values):** None (display setting)
**Usage examples:**
```
VARIABLE CONTRAST 50
VARIABLE WRITE
END
```
**Description:** Adjusts LCD display contrast for optimal visibility.
---
### VARIABLE CPL_COMMAND_MASK
**Command name:** `VARIABLE CPL_COMMAND_MASK mask` or `V CPL_COMMAND_MASK mask`
**Alternative formats:** None
**Parameters (Inputs):**
- `mask` - CPL command filtering mask value (UIntRange)
**Outputs (Return values):** None (command filtering)
**Usage examples:**
```
VARIABLE CPL_COMMAND_MASK 255
VARIABLE WRITE
END
```
**Description:** Controls which CPL commands are accepted by the printer.
---
### VARIABLE DARKNESS
**Command name:** `VARIABLE DARKNESS darkness` or `V D darkness`
**Alternative formats:** `V DARKNESS level` (extended)
**Parameters (Inputs):**
- `darkness` or `level` - Print darkness level (DarkRange: 0-30)
**Outputs (Return values):** None (print quality setting)
**Usage examples:**
```
VARIABLE DARKNESS 10
VARIABLE WRITE
END
```
```
VARIABLE DARKNESS 15
VARIABLE WRITE
END
```
**Description:** Adjusts print darkness for optimal print quality.
---
### VARIABLE DHCP
**Command name:** `VARIABLE DHCP` (with sub-commands) or `V DHCP`
**Alternative formats:** Various DHCP settings
**Parameters (Inputs):** DHCP configuration parameters (e.g., ON)
**Outputs (Return values):** DHCP status if queried
**Usage examples:**
```
VARIABLE DHCP ON
VARIABLE WRITE
END
```
**Description:** Configures DHCP client settings for network connectivity.
---
### VARIABLE ETHERNET BOOTP
**Command name:** `VARIABLE ETHERNET BOOTP mode` or `V ETHERNET BOOTP mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - BOOTP protocol enable/disable (ON/OFF)
**Outputs (Return values):** None (network protocol)
**Usage examples:**
```
VARIABLE ETHERNET BOOTP OFF
VARIABLE WRITE
END
```
**Description:** Enables or disables BOOTP protocol for network configuration.
---
### VARIABLE ETHERNET DHCP_CRIT
**Command name:** `VARIABLE ETHERNET DHCP_CRIT mode` or `V ETHERNET DHCP_CRIT mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - DHCP critical mode setting (ENABLED/DISABLED)
**Outputs (Return values):** None (network behavior)
**Usage examples:**
```
VARIABLE ETHERNET DHCP_CRIT ENABLED
VARIABLE WRITE
END
```
**Description:** Configures DHCP critical operation mode behavior.
---
### VARIABLE ETHERNET DHCP_OFFERS
**Command name:** `VARIABLE ETHERNET DHCP_OFFERS count` or `V ETHERNET DHCP_OFFERS count`
**Alternative formats:** None
**Parameters (Inputs):**
- `count` - Number of DHCP offers to wait for (UIntRange)
**Outputs (Return values):** None (network timing)
**Usage examples:**
```
VARIABLE ETHERNET DHCP_OFFERS 3
VARIABLE WRITE
END
```
**Description:** Sets how many DHCP offers to collect before accepting one.
---
### VARIABLE ETHERNET FIRMWARE
**Command name:** `VARIABLE ETHERNET FIRMWARE` or `V ETHERNET FIRMWARE`
**Alternative formats:** None
**Parameters (Inputs):** None (query only)
**Outputs (Return values):** Ethernet firmware version
**Usage examples:**
```
VARIABLE ETHERNET FIRMWARE
END
```
**Description:** Queries the Ethernet module firmware version.
---
### VARIABLE ETHERNET GARP
**Command name:** `VARIABLE ETHERNET GARP mode` or `V ETHERNET GARP mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Gratuitous ARP enable/disable (ON/OFF)
**Outputs (Return values):** None (network protocol)
**Usage examples:**
```
VARIABLE ETHERNET GARP ON
VARIABLE WRITE
END
```
**Description:** Enables gratuitous ARP broadcasts for network presence.
---
### VARIABLE ETHERNET GATEWAY
**Command name:** `VARIABLE ETHERNET GATEWAY a.b.c.d` or `V ETHERNET GATEWAY a.b.c.d`
**Alternative formats:** `VARIABLE GATEWAY address` (general)
**Parameters (Inputs):**
- `a.b.c.d` - Gateway IP address
**Outputs (Return values):** None (network routing)
**Usage examples:**
```
VARIABLE ETHERNET GATEWAY 192.168.1.1
VARIABLE WRITE
END
```
**Description:** Sets the default gateway IP address for network routing.
---
### VARIABLE ETHERNET IP ADDRESS
**Command name:** `VARIABLE ETHERNET IP ADDRESS a.b.c.d` or `V ETHERNET IP ADDRESS a.b.c.d`
**Alternative formats:** `VARIABLE IPADDR address`
**Parameters (Inputs):**
- `a.b.c.d` - IP address
**Outputs (Return values):** None (network addressing)
**Usage examples:**
```
VARIABLE ETHERNET IP ADDRESS 192.168.1.100
VARIABLE WRITE
END
```
**Description:** Sets the printer's static IP address.
---
### VARIABLE ETHERNET JOBSOKINERROR
**Command name:** `VARIABLE ETHERNET JOBSOKINERROR mode` or `V ETHERNET JOBSOKINERROR mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Jobs OK in error mode setting (ENABLED/DISABLED)
**Outputs (Return values):** None (error handling)
**Usage examples:**
```
VARIABLE ETHERNET JOBSOKINERROR ENABLED
VARIABLE WRITE
END
```
**Description:** Controls whether print jobs are accepted during error conditions.
---
### VARIABLE ETHERNET LPD
**Command name:** `VARIABLE ETHERNET LPD mode` or `V ETHERNET LPD mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - LPD protocol enable/disable (ON/OFF)
**Outputs (Return values):** None (print protocol)
**Usage examples:**
```
VARIABLE ETHERNET LPD ON
VARIABLE WRITE
END
```
**Description:** Enables or disables LPD (Line Printer Daemon) protocol.
---
### VARIABLE ETHERNET NETMASK
**Command name:** `VARIABLE ETHERNET NETMASK a.b.c.d` or `V ETHERNET NETMASK a.b.c.d`
**Alternative formats:** `VARIABLE NETMASK mask`
**Parameters (Inputs):**
- `a.b.c.d` - Subnet mask
**Outputs (Return values):** None (network configuration)
**Usage examples:**
```
VARIABLE ETHERNET NETMASK 255.255.255.0
VARIABLE WRITE
END
```
**Description:** Sets the network subnet mask.
---
### VARIABLE ETHERNET RESET
**Command name:** `VARIABLE ETHERNET RESET` or `V ETHERNET RESET`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Reset confirmation
**Usage examples:**
```
VARIABLE ETHERNET RESET
END
```
**Description:** Resets the Ethernet module to default settings.
---
### VARIABLE ETHERNET RESET COMMUNITY
**Command name:** `VARIABLE ETHERNET RESET COMMUNITY` or `V ETHERNET RESET COMMUNITY`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Reset confirmation
**Usage examples:**
```
VARIABLE ETHERNET RESET COMMUNITY
END
```
**Description:** Resets SNMP community strings to default values.
---
### VARIABLE ETHERNET RTEL
**Command name:** `VARIABLE ETHERNET RTEL mode` or `V ETHERNET RTEL mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Remote telnet enable/disable (ON/OFF)
**Outputs (Return values):** None (remote access)
**Usage examples:**
```
VARIABLE ETHERNET RTEL ON
VARIABLE WRITE
END
```
**Description:** Enables or disables remote telnet access.
---
### VARIABLE ETHERNET RTEL PORT
**Command name:** `VARIABLE ETHERNET RTEL PORT port` or `V ETHERNET RTEL PORT port`
**Alternative formats:** None
**Parameters (Inputs):**
- `port` - Telnet port number (UIntRange)
**Outputs (Return values):** None (network service)
**Usage examples:**
```
VARIABLE ETHERNET RTEL PORT 23
VARIABLE WRITE
END
```
**Description:** Sets the TCP port number for telnet service.
---
### VARIABLE ETHERNET RTEL TIMEOUT
**Command name:** `VARIABLE ETHERNET RTEL TIMEOUT seconds` or `V ETHERNET RTEL TIMEOUT seconds`
**Alternative formats:** None
**Parameters (Inputs):**
- `seconds` - Telnet session timeout in seconds (UIntRange)
**Outputs (Return values):** None (session management)
**Usage examples:**
```
VARIABLE ETHERNET RTEL TIMEOUT 300
VARIABLE WRITE
END
```
**Description:** Sets automatic telnet session timeout.
---
### VARIABLE ETHERNET SNMP
**Command name:** `VARIABLE ETHERNET SNMP mode` or `V ETHERNET SNMP mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - SNMP protocol enable/disable (ON/OFF)
**Outputs (Return values):** None (network management)
**Usage examples:**
```
VARIABLE ETHERNET SNMP ON
VARIABLE WRITE
END
```
**Description:** Enables or disables SNMP network management protocol.
---
### VARIABLE ETHERNET TELNET
**Command name:** `VARIABLE ETHERNET TELNET mode` or `V ETHERNET TELNET mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Telnet service enable/disable (ON/OFF)
**Outputs (Return values):** None (remote access)
**Usage examples:**
```
VARIABLE ETHERNET TELNET ON
VARIABLE WRITE
END
```
**Description:** Enables or disables telnet service.
---
### VARIABLE ETHERNET TELNET TIMEOUT
**Command name:** `VARIABLE ETHERNET TELNET TIMEOUT seconds` or `V ETHERNET TELNET TIMEOUT seconds`
**Alternative formats:** None
**Parameters (Inputs):**
- `seconds` - Telnet connection timeout in seconds (UIntRange)
**Outputs (Return values):** None (connection management)
**Usage examples:**
```
VARIABLE ETHERNET TELNET TIMEOUT 180
VARIABLE WRITE
END
```
**Description:** Sets telnet connection idle timeout.
---
### VARIABLE ETHERNET TEXT BUFFER
**Command name:** `VARIABLE ETHERNET TEXT BUFFER size` or `V ETHERNET TEXT BUFFER size`
**Alternative formats:** None
**Parameters (Inputs):**
- `size` - Network text buffer size in bytes (UIntRange)
**Outputs (Return values):** None (buffer management)
**Usage examples:**
```
VARIABLE ETHERNET TEXT BUFFER 8192
VARIABLE WRITE
END
```
**Description:** Sets the Ethernet interface text buffer size.
---
### VARIABLE EPL_COMMAND_MASK
**Command name:** `VARIABLE EPL_COMMAND_MASK mask` or `V EPL_COMMAND_MASK mask`
**Alternative formats:** None
**Parameters (Inputs):**
- `mask` - EPL command filtering mask value (UIntRange)
**Outputs (Return values):** None (command filtering)
**Usage examples:**
```
VARIABLE EPL_COMMAND_MASK 0
VARIABLE WRITE
END
```
**Description:** Controls which EPL commands are accepted by the printer.
---
### VARIABLE ERROR_LEVEL
**Command name:** `VARIABLE ERROR_LEVEL level` or `V ERROR_LEVEL level`
**Alternative formats:** None
**Parameters (Inputs):**
- `level` - Error level setting (ErrorLvlEnum: 0-3)
**Outputs (Return values):** None (configuration command)
**Usage examples:**
```
VARIABLE ERROR_LEVEL 1
VARIABLE WRITE
END
```
**Description:** Sets the error reporting level for the printer.
---
### VARIABLE FACTORY_RESTORE
**Command name:** `VARIABLE FACTORY_RESTORE` or `V FACTORY_RESTORE`
**Alternative formats:** None
**Parameters (Inputs):** Restore parameters (e.g., ALL)
**Outputs (Return values):** Restore operation results
**Usage examples:**
```
VARIABLE FACTORY_RESTORE ALL
END
```
**Description:** Restores printer to factory default settings.
---
### VARIABLE FEED
**Command name:** `VARIABLE FEED amount` or `V FEED amount`
**Alternative formats:** None
**Parameters (Inputs):**
- `amount` - Feed distance in dots (UIntRange)
**Outputs (Return values):** None (media control)
**Usage examples:**
```
VARIABLE FEED 100
VARIABLE WRITE
END
```
**Description:** Sets default feed distance for media advance.
---
### VARIABLE FEED_BUTTON
**Command name:** `VARIABLE FEED_BUTTON mode` or `V FEED_BUTTON mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Feed button behavior configuration (ENABLE/DISABLE)
**Outputs (Return values):** None (button control)
**Usage examples:**
```
VARIABLE FEED_BUTTON ENABLE
VARIABLE WRITE
END
```
**Description:** Configures the behavior of the manual feed button.
---
### VARIABLE FEED_CONFIG
**Command name:** `VARIABLE FEED_CONFIG P,D / ?` or `V FEED_CONFIG P,D / ?`
**Alternative formats:** None
**Parameters (Inputs):**
- `P` - Feed type (e.g., L for continuous)
- `D` - Print mode (e.g., F for direct thermal)
- `?` - Query current settings
**Outputs (Return values):** Current feed configuration if queried
**Usage examples:**
```
VARIABLE FEED_CONFIG L,F
VARIABLE WRITE
END
```
**Description:** Configures feed type and print mode combination.
---
### VARIABLE FEED_SPEED
**Command name:** `VARIABLE FEED_SPEED speed` or `V FEED_SPEED speed`
**Alternative formats:** None
**Parameters (Inputs):**
- `speed` - Feed speed (PrintSpeedRange: 0-65535)
**Outputs (Return values):** None (speed setting)
**Usage examples:**
```
VARIABLE FEED_SPEED 6000
VARIABLE WRITE
END
```
**Description:** Sets the media feed speed in milli-IPS.
---
### VARIABLE FEED_TYPE
**Command name:** `VARIABLE FEED_TYPE type` or `V FEED_TYPE type`
**Alternative formats:** None
**Parameters (Inputs):**
- `type` - Media feed type (GAP, NOTCH, CONTINUOUS, MARK)
**Outputs (Return values):** None (media detection)
**Usage examples:**
```
VARIABLE FEED_TYPE GAP
VARIABLE WRITE
END
```
**Description:** Sets the media type for proper feed detection.
---
### VARIABLE GAP_SIZE
**Command name:** `VARIABLE GAP_SIZE nnn` or `V GAP_SIZE nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Gap size in dots (UIntRange)
**Outputs (Return values):** None (media setting)
**Usage examples:**
```
VARIABLE GAP_SIZE 50
VARIABLE WRITE
END
```
**Description:** Sets the gap size between labels for gap media.
---
### VARIABLE HIGHSPEED
**Command name:** `VARIABLE HIGHSPEED mode` or `V HIGHSPEED mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - High-speed communication mode (ON/OFF)
**Outputs (Return values):** None (performance setting)
**Usage examples:**
```
VARIABLE HIGHSPEED ON
VARIABLE WRITE
END
```
**Description:** Enables high-speed data transfer mode.
---
### VARIABLE INDEX
**Command name:** `VARIABLE INDEX on/off/?` or `V INDEX on/off/?`
**Alternative formats:** `INDEX` or `I` (toggle); `V INDEX value` (adjustment)
**Parameters (Inputs):**
- `on/off/?` - Index enable/disable setting (OnOffStat)
- `value` (adjustment) - Index sensor adjustment value (IntRange)
**Outputs (Return values):** Current index status if queried
**Usage examples:**
```
VARIABLE INDEX ON
VARIABLE WRITE
END
```
```
VARIABLE INDEX 50
VARIABLE WRITE
END
```
**Description:** Enables or disables label indexing functionality; adjusts sensor sensitivity.
---
### VARIABLE INDEX SETTING
**Command name:** `VARIABLE INDEX SETTING CALIBRATE` or `V INDEX SETTING CALIBRATE`
**Alternative formats:** `VARIABLE INDEX SETTING [parameters]` (extended); `V INDEX SETTING params`
**Parameters (Inputs):**
- `CALIBRATE` - Initiates index calibration
- Additional parameters for manual index setting (e.g., GAP,DIRECT,50,50,100,200)
**Outputs (Return values):** None (calibration command)
**Usage examples:**
```
VARIABLE INDEX SETTING CALIBRATE
VARIABLE WRITE
END
```
```
VARIABLE INDEX SETTING GAP,DIRECT,50,50,100,200
VARIABLE WRITE
END
```
**Description:** Calibrates or manually sets index parameters for label detection.
---
### VARIABLE IRDA
**Command name:** `VARIABLE IRDA mode` or `V IRDA mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - IrDA infrared interface control (ON/OFF)
**Outputs (Return values):** None (interface control)
**Usage examples:**
```
VARIABLE IRDA ON
VARIABLE WRITE
END
```
**Description:** Enables or disables infrared communication interface.
---
### VARIABLE IRDA COMM
**Command name:** `VARIABLE IRDA COMM settings` or `V IRDA COMM settings`
**Alternative formats:** None
**Parameters (Inputs):**
- `settings` - IrDA communication parameters (e.g., 115200)
**Outputs (Return values):** None (infrared configuration)
**Usage examples:**
```
VARIABLE IRDA COMM 115200
VARIABLE WRITE
END
```
**Description:** Configures IrDA communication speed and parameters.
---
### VARIABLE IRDA PROTOCOL
**Command name:** `VARIABLE IRDA PROTOCOL protocol` or `V IRDA PROTOCOL protocol`
**Alternative formats:** None
**Parameters (Inputs):**
- `protocol` - IrDA protocol selection (UIntRange)
**Outputs (Return values):** None (protocol setting)
**Usage examples:**
```
VARIABLE IRDA PROTOCOL 1
VARIABLE WRITE
END
```
**Description:** Selects the IrDA protocol version and mode.
---
### VARIABLE KBLAYOUT
**Command name:** `VARIABLE KBLAYOUT mode` or `V KBLAYOUT mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Keyboard layout (0-9, ? for query): 0=US English, 1=Canadian French, etc.
**Outputs (Return values):** Current layout if queried
**Usage examples:**
```
VARIABLE KBLAYOUT 4
VARIABLE WRITE
END
```
**Description:** Selects keyboard layout for data entry.
---
### VARIABLE LABEL_LENGTH
**Command name:** `VARIABLE LABEL_LENGTH nnn` or `V LABEL_LENGTH nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Label length in dots (UIntRange)
**Outputs (Return values):** None (dimension setting)
**Usage examples:**
```
VARIABLE LABEL_LENGTH 800
VARIABLE WRITE
END
```
**Description:** Sets fixed label length for media handling.
---
### VARIABLE LANGUAGE
**Command name:** `VARIABLE LANGUAGE code` or `V LANGUAGE code`
**Alternative formats:** None
**Parameters (Inputs):**
- `code` - Language code for printer firmware (e.g., EN)
**Outputs (Return values):** None (localization)
**Usage examples:**
```
VARIABLE LANGUAGE EN
VARIABLE WRITE
END
```
**Description:** Sets the firmware language for status messages.
---
### VARIABLE LIMIT_PERCENT
**Command name:** `VARIABLE LIMIT_PERCENT` or `V LIMIT_PERCENT`
**Alternative formats:** None
**Parameters (Inputs):** Various developer parameters (e.g., 90)
**Outputs (Return values):** None (developer setting)
**Usage examples:**
```
VARIABLE LIMIT_PERCENT 90
VARIABLE WRITE
END
```
**Description:** Developer command to limit maximum strobe duty cycle during printing.
---
### VARIABLE LOWSPEED
**Command name:** `VARIABLE LOWSPEED mode` or `V LOWSPEED mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Low-speed communication mode (ON/OFF)
**Outputs (Return values):** None (performance setting)
**Usage examples:**
```
VARIABLE LOWSPEED OFF
VARIABLE WRITE
END
```
**Description:** Enables low-speed communication mode for compatibility.
---
### VARIABLE MAX_WIDTH
**Command name:** `VARIABLE MAX_WIDTH nnn` or `V MAX_WIDTH nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Maximum print width in dots (UIntRange)
**Outputs (Return values):** None (dimension limit)
**Usage examples:**
```
VARIABLE MAX_WIDTH 832
VARIABLE WRITE
END
```
**Description:** Sets the maximum printable width limit to prevent edge overflow.
---
### VARIABLE MEASURE_LABEL
**Command name:** `VARIABLE MEASURE_LABEL mode` or `V MEASURE_LABEL mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Enable/disable automatic label measurement during calibration (ON/OFF)
**Outputs (Return values):** None (calibration setting)
**Usage examples:**
```
VARIABLE MEASURE_LABEL ON
VARIABLE WRITE
END
```
**Description:** Enables automatic label dimension measurement during media calibration process.
---
### VARIABLE MEDIA_ADJUST
**Command name:** `VARIABLE MEDIA_ADJUST value` or `V MEDIA_ADJUST value`
**Alternative formats:** None
**Parameters (Inputs):**
- `value` - Media sensor adjustment value (IntRange)
**Outputs (Return values):** None (sensor calibration)
**Usage examples:**
```
VARIABLE MEDIA_ADJUST 10
VARIABLE WRITE
END
```
**Description:** Fine-tunes media sensor detection thresholds.
---
### VARIABLE MENU_LANGUAGE
**Command name:** `VARIABLE MENU_LANGUAGE mode` or `V MENU_LANGUAGE mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Language mode number (UIntRange)
**Outputs (Return values):** None (localization)
**Usage examples:**
```
VARIABLE MENU_LANGUAGE 1
VARIABLE WRITE
END
```
**Description:** Sets the language for printer menus and messages.
---
### VARIABLE MIRROR_LABEL
**Command name:** `VARIABLE MIRROR_LABEL mode` or `V MIRROR_LABEL mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Mirror/flip label horizontally (ON/OFF)
**Outputs (Return values):** None (print orientation)
**Usage examples:**
```
VARIABLE MIRROR_LABEL ON
VARIABLE WRITE
END
```
**Description:** Mirrors the entire label horizontally.
---
### VARIABLE MODE
**Command name:** `VARIABLE MODE mode [scale]` or `V MODE mode [scale]`
**Alternative formats:** `V MODE type` (extended)
**Parameters (Inputs):**
- `mode` - Printer mode (ModeRange: 0-2) or type (THERMAL/DIRECT)
- `scale` (optional) - Mode scale (ModeScaleRange: 0-255)
**Outputs (Return values):** None (mode setting)
**Usage examples:**
```
VARIABLE MODE 1
VARIABLE WRITE
END
```
```
VARIABLE MODE THERMAL
VARIABLE WRITE
END
```
**Description:** Sets the printer operating mode.
---
### VARIABLE NO_MEDIA
**Command name:** `VARIABLE NO_MEDIA length` or `V NO_MEDIA length`
**Alternative formats:** `V NO_MEDIA action` (extended)
**Parameters (Inputs):**
- `length` - No media length in inches (FloatRange)
- `action` (extended) - Action to take when media is not detected (e.g., HALT)
**Outputs (Return values):** None (media setting)
**Usage examples:**
```
VARIABLE NO_MEDIA 1
VARIABLE WRITE
END
```
```
VARIABLE NO_MEDIA HALT
VARIABLE WRITE
END
```
**Description:** Sets the no media condition handling.
---
### VARIABLE NORMAL
**Command name:** `VARIABLE NORMAL` or `V NORMAL`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (reset to defaults)
**Usage examples:**
```
VARIABLE NORMAL
VARIABLE WRITE
END
```
**Description:** Resets settings to normal/default values.
---
### VARIABLE OFF AFTER
**Command name:** `VARIABLE OFF_AFTER seconds` or `V OFF_AFTER seconds`
**Alternative formats:** `V OFF AFTER minutes` (extended)
**Parameters (Inputs):**
- `seconds` or `minutes` - Auto-off time in seconds/minutes (SleepRange: 0-255)
**Outputs (Return values):** None (power management)
**Usage examples:**
```
VARIABLE OFF_AFTER 300
VARIABLE WRITE
END
```
```
VARIABLE OFF AFTER 30
VARIABLE WRITE
END
```
**Description:** Sets automatic power-off timer for energy conservation.
---
### VARIABLE ON TIME
**Command name:** `VARIABLE ON_TIME seconds` or `V ON_TIME seconds`
**Alternative formats:** `V ON_TIME milliseconds` (extended)
**Parameters (Inputs):**
- `seconds` or `milliseconds` - On time in seconds/milliseconds (OnTimeRange: 0-65535)
**Outputs (Return values):** None (power management)
**Usage examples:**
```
VARIABLE ON_TIME 60
VARIABLE WRITE
END
```
```
VARIABLE ON_TIME 2
VARIABLE WRITE
END
```
**Description:** Sets the duration the printer remains active after last command; or print head energizing time.
---
### VARIABLE ON/OFF
**Command name:** `VARIABLE ON/OFF mode` or `V ON/OFF mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Power control (ON/OFF)
**Outputs (Return values):** None (power control)
**Usage examples:**
```
VARIABLE ON/OFF ON
VARIABLE WRITE
END
```
**Description:** Controls printer power state.
---
### VARIABLE OOP_TLED
**Command name:** `VARIABLE OOP_TLED mode` or `V OOP_TLED mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Out-of-paper transmissive LED setting (UIntRange)
**Outputs (Return values):** None (sensor configuration)
**Usage examples:**
```
VARIABLE OOP_TLED 1
VARIABLE WRITE
END
```
**Description:** Configures the transmissive LED for out-of-paper detection.
---
### VARIABLE POSITION
**Command name:** `VARIABLE POSITION pos` or `V POSITION pos`
**Alternative formats:** `V POSITION x,y` (extended)
**Parameters (Inputs):**
- `pos` - Position offset in motor steps (IntRange)
- `x,y` (extended) - X and Y offset positions
**Outputs (Return values):** None (position adjustment)
**Usage examples:**
```
VARIABLE POSITION 50
VARIABLE WRITE
END
```
```
VARIABLE POSITION 10,5
VARIABLE WRITE
END
```
**Description:** Adjusts the top of form position by specified motor steps.
---
### VARIABLE PRESENTLABEL
**Command name:** `VARIABLE PRESENTLABEL mode` or `V PRESENTLABEL mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Label presentation mode (ON/OFF)
**Outputs (Return values):** None (peeler control)
**Usage examples:**
```
VARIABLE PRESENTLABEL ON
VARIABLE WRITE
END
```
**Description:** Enables label presentation mode for peeler operation.
---
### VARIABLE PRINT_MODE
**Command name:** `VARIABLE PRINT_MODE mode` or `V PRINT_MODE mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Print mode (PrintModes: Auto, TT, DT; or DIRECT/THERMAL)
**Outputs (Return values):** None (print mode setting)
**Usage examples:**
```
VARIABLE PRINT_MODE TT
VARIABLE WRITE
END
```
```
VARIABLE PRINT_MODE THERMAL
VARIABLE WRITE
END
```
**Description:** Sets the print mode to Thermal Transfer (TT), Direct Thermal (DT), or Auto.
---
### VARIABLE PRINT_SPEED
**Command name:** `VARIABLE PRINT_SPEED speed` or `V PRINT_SPEED speed`
**Alternative formats:** `V PRINT_SPEED ips` (extended)
**Parameters (Inputs):**
- `speed` or `ips` - Print speed in milli-IPS or inches per second (PrintSpeedRange: 0-65535)
**Outputs (Return values):** None (speed setting)
**Usage examples:**
```
VARIABLE PRINT_SPEED 8000
VARIABLE WRITE
END
```
```
VARIABLE PRINT_SPEED 4
VARIABLE WRITE
END
```
**Description:** Sets the print speed in milli-IPS (inches per second).
---
### VARIABLE READ
**Command name:** `VARIABLE READ` or `V READ`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current variable settings
**Usage examples:**
```
VARIABLE READ
END
```
**Description:** Displays current variable settings.
---
### VARIABLE RECALIBRATE
**Command name:** `VARIABLE RECALIBRATE` or `V RECALIBRATE`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Calibration results
**Usage examples:**
```
VARIABLE RECALIBRATE
END
```
**Description:** Initiates media calibration sequence.
---
### VARIABLE REPRINT
**Command name:** `VARIABLE REPRINT on/off` or `V REPRINT on/off`
**Alternative formats:** `V REPRINT mode` (extended)
**Parameters (Inputs):**
- `on/off` or `mode` - Reprint enable/disable setting (ON/OFF)
**Outputs (Return values):** None (reprint control)
**Usage examples:**
```
VARIABLE REPRINT ON
VARIABLE WRITE
END
```
**Description:** Enables or disables label reprint functionality.
---
### VARIABLE REPORT_LEVEL
**Command name:** `VARIABLE REPORT_LEVEL level` or `V REPORT_LEVEL level`
**Alternative formats:** None
**Parameters (Inputs):**
- `level` - Status reporting level (0-3)
**Outputs (Return values):** None (reporting control)
**Usage examples:**
```
VARIABLE REPORT_LEVEL 2
VARIABLE WRITE
END
```
**Description:** Sets the verbosity level for status reporting.
---
### VARIABLE REPORT_TYPE
**Command name:** `VARIABLE REPORT_TYPE type` or `V REPORT_TYPE type`
**Alternative formats:** None
**Parameters (Inputs):**
- `type` - Type of status reports to generate (e.g., FULL)
**Outputs (Return values):** None (reporting control)
**Usage examples:**
```
VARIABLE REPORT_TYPE FULL
VARIABLE WRITE
END
```
**Description:** Specifies which types of status reports are generated.
---
### VARIABLE RESET
**Command name:** `VARIABLE RESET` or `V RESET`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (reset command)
**Usage examples:**
```
VARIABLE RESET
END
```
**Description:** Resets printer variables to default values.
---
### VARIABLE ROTATE_LABEL
**Command name:** `VARIABLE ROTATE_LABEL degrees` or `V ROTATE_LABEL degrees`
**Alternative formats:** None
**Parameters (Inputs):**
- `degrees` - Rotation angle (0, 90, 180, 270)
**Outputs (Return values):** None (print orientation)
**Usage examples:**
```
VARIABLE ROTATE_LABEL 180
VARIABLE WRITE
END
```
**Description:** Rotates the entire label by specified degrees.
---
### VARIABLE SCRIPT_INPUT_RESET
**Command name:** `VARIABLE SCRIPT_INPUT_RESET mode` or `V SCRIPT_INPUT_RESET mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Script input buffer reset mode (ON/OFF)
**Outputs (Return values):** None (buffer management)
**Usage examples:**
```
VARIABLE SCRIPT_INPUT_RESET ON
VARIABLE WRITE
END
```
**Description:** Controls automatic script input buffer reset behavior.
---
### VARIABLE SHIFT LEFT
**Command name:** `VARIABLE SHIFT LEFT dots` or `V SHIFT LEFT dots`
**Alternative formats:** None
**Parameters (Inputs):**
- `dots` - Number of dots to shift left (UIntRange)
**Outputs (Return values):** None (position adjustment)
**Usage examples:**
```
VARIABLE SHIFT LEFT 10
VARIABLE WRITE
END
```
**Description:** Shifts entire print image left by specified dots.
---
### VARIABLE SLEEP_AFTER
**Command name:** `VARIABLE SLEEP_AFTER seconds` or `V SLEEP_AFTER seconds`
**Alternative formats:** `V SLEEP_AFTER minutes` (extended)
**Parameters (Inputs):**
- `seconds` or `minutes` - Sleep delay in seconds/minutes (SleepRange: 0-255)
**Outputs (Return values):** None (timing control)
**Usage examples:**
```
VARIABLE SLEEP_AFTER 5
VARIABLE WRITE
END
```
```
VARIABLE SLEEP_AFTER 15
VARIABLE WRITE
END
```
**Description:** Sets delay after label completion or automatic sleep mode after idle.
---
### VARIABLE SNMP SETTINGS
**Command name:** Various SNMP configuration commands or `V SNMP`
**Alternative formats:** None
**Parameters (Inputs):** SNMP parameters (addresses, communities, etc.)
**Outputs (Return values):** SNMP status if queried
**Usage examples:**
```
VARIABLE SNMP_ACTIVE ON
VARIABLE SNMP_ADDR 192.168.1.100
VARIABLE WRITE
END
```
**Description:** Configures SNMP for network management.
---
### VARIABLE TERMINAL
**Command name:** `VARIABLE TERMINAL mode` or `V TERMINAL mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Terminal emulation mode (e.g., VT100)
**Outputs (Return values):** None (interface mode)
**Usage examples:**
```
VARIABLE TERMINAL VT100
VARIABLE WRITE
END
```
**Description:** Sets terminal emulation mode for interactive communication.
---
### VARIABLE TIME
**Command name:** `VARIABLE TIME hh:mm:ss` or `V TIME hh:mm:ss`
**Alternative formats:** None
**Parameters (Inputs):**
- `hh:mm:ss` - Time in 24-hour format
**Outputs (Return values):** None (time setting)
**Usage examples:**
```
VARIABLE TIME 14:30:00
VARIABLE WRITE
END
```
**Description:** Sets the printer's internal clock time.
---
### VARIABLE TOF
**Command name:** `VARIABLE TOF value` or `V TOF value`
**Alternative formats:** None
**Parameters (Inputs):**
- `value` - Top-of-form offset value (IntRange)
**Outputs (Return values):** None (media positioning)
**Usage examples:**
```
VARIABLE TOF 50
VARIABLE WRITE
END
```
**Description:** Adjusts the top-of-form position offset.
---
### VARIABLE TXTBFR
**Command name:** `VARIABLE TXTBFR size` or `V TXTBFR size`
**Alternative formats:** None
**Parameters (Inputs):**
- `size` - Text buffer size in bytes (UIntRange)
**Outputs (Return values):** None (buffer management)
**Usage examples:**
```
VARIABLE TXTBFR 2048
VARIABLE WRITE
END
```
**Description:** Sets the size of the text input buffer.
---
### VARIABLE USB_TXTBFR
**Command name:** `VARIABLE USB_TXTBFR size` or `V USB_TXTBFR size`
**Alternative formats:** None
**Parameters (Inputs):**
- `size` - USB text buffer size in bytes (UIntRange)
**Outputs (Return values):** None (buffer management)
**Usage examples:**
```
VARIABLE USB_TXTBFR 4096
VARIABLE WRITE
END
```
**Description:** Sets the USB interface text buffer size.
---
### VARIABLE USER_FEEDBACK
**Command name:** `VARIABLE USER_FEEDBACK mode` or `V USER_FEEDBACK mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - User feedback mode (VISUAL/AUDIO/BOTH/NONE)
**Outputs (Return values):** None (user interface)
**Usage examples:**
```
VARIABLE USER_FEEDBACK BOTH
VARIABLE WRITE
END
```
**Description:** Configures user feedback mechanisms (beeper, LED, etc.).
---
### VARIABLE WIDTH
**Command name:** `VARIABLE WIDTH width` or `V WIDTH width`
**Alternative formats:** `V WIDTH dots` (extended)
**Parameters (Inputs):**
- `width` or `dots` - Label width in user units/dots (WidthRange: 0-65535)
**Outputs (Return values):** None (width setting)
**Usage examples:**
```
VARIABLE WIDTH 400
VARIABLE WRITE
END
```
```
VARIABLE WIDTH 832
VARIABLE WRITE
END
```
**Description:** Sets the label width for formatting purposes.
---
### VARIABLE WRITE
**Command name:** `VARIABLE WRITE` or `V WRITE`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (save command)
**Usage examples:**
```
VARIABLE DARKNESS 10
VARIABLE WRITE
END
```
**Description:** Saves current variable settings to non-volatile memory.
---
### VARIABLE ZPL_COMMAND_MASK
**Command name:** `VARIABLE ZPL_COMMAND_MASK mask` or `V ZPL_COMMAND_MASK mask`
**Alternative formats:** None
**Parameters (Inputs):**
- `mask` - ZPL command filtering mask value (UIntRange)
**Outputs (Return values):** None (command filtering)
**Usage examples:**
```
VARIABLE ZPL_COMMAND_MASK 0
VARIABLE WRITE
END
```
**Description:** Controls which ZPL commands are accepted by the printer.
---
## Object and Storage Commands
Commands for managing stored objects, formats, and memory. Sorted lexicographically.
### ALL OBJECT DELETE
**Command name:** `!I 3` or `ALLOBJECTSDELETE`
**Alternative formats:** `!F`
**Parameters (Inputs):** None
**Outputs (Return values):** Delete operation results
**Usage examples:**
```
!I 3
END
```
**Description:** Deletes all stored objects from printer memory.
---
### OBJECT DELETE
**Command name:** `!DELETE ID` or `!D ID`
**Alternative formats:** `OBJECT_DELETE ID`
**Parameters (Inputs):**
- `ID` - Object identifier (IDSize)
**Outputs (Return values):** None (object management)
**Usage examples:**
```
!DELETE MYFONT
END
```
**Description:** Deletes a stored object from printer memory.
---
### OBJECT INFO
**Command name:** `!OBJECT INFO id type ver` or `!OI id type ver`
**Alternative formats:** `!OBJECT INFO objectname` (extended)
**Parameters (Inputs):**
- `id` or `objectname` - Object identifier
- `type` - Object type (UIntRange)
- `ver` - Version information (UIntRange)
**Outputs (Return values):** Object information
**Usage examples:**
```
!OBJECT INFO MYFONT 1 100
END
```
```
!OBJECT INFO LOGO1
```
**Description:** Retrieves information about a specific stored object.
---
### OBJECT LIST
**Command name:** `!LS` or `OBJECT_LIST`
**Alternative formats:** `!LS`; `OBJECT LIST SERIAL` or `EPL OBJECT LIST SERIAL`
**Parameters (Inputs):** None
**Outputs (Return values):** List of stored objects (with serial numbers in extended)
**Usage examples:**
```
!LS
END
```
**Description:** Lists all objects stored in printer memory.
---
### OBJECT MARK
**Command name:** `!OBJECT MARK ID` or `!OM ID`
**Alternative formats:** None
**Parameters (Inputs):**
- `ID` - Object identifier to mark for deletion (IDSize)
**Outputs (Return values):** None (mark operation)
**Usage examples:**
```
!OBJECT MARK OLDFONT
!OBJECT PACK
END
```
**Description:** Marks an object for deletion (requires OBJECT PACK to actually delete).
---
### OBJECT MARK TYPE
**Command name:** `!OBJECT MARK_TYPE TYPE` or `!OMT TYPE`
**Alternative formats:** None
**Parameters (Inputs):**
- `TYPE` - Object type to mark for deletion (e.g., FONT)
**Outputs (Return values):** None (mark operation)
**Usage examples:**
```
!OBJECT MARK_TYPE FONT
!OBJECT PACK
END
```
**Description:** Marks all objects of specified type for deletion.
---
### OBJECT MEMORY_REMAINING
**Command name:** `!OBJECT MEMORY_REMAINING`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Available object memory in bytes
**Usage examples:**
```
!OBJECT MEMORY_REMAINING
```
**Description:** Reports available memory for object storage.
---
### OBJECT PACK
**Command name:** `!OBJECT PACK` or `!OP`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Pack operation results
**Usage examples:**
```
!OBJECT MARK OLDFONT
!OBJECT PACK
END
```
**Description:** Removes objects marked for deletion and compacts flash memory.
---
### OBJECT UPLOAD
**Command name:** `!OBJECT UPLOAD LOC ID` or `!OU LOC ID`
**Alternative formats:** `!OBJECT UPLOAD objectname` (extended)
**Parameters (Inputs):**
- `LOC` - Storage location (UIntRange)
- `ID` or `objectname` - Object identifier
**Outputs (Return values):** Upload operation results or object data stream
**Usage examples:**
```
!OBJECT UPLOAD 3 NEWFONT
[font data]
END
```
```
!OBJECT UPLOAD LOGO1
```
**Description:** Uploads object data to specified storage location or streams stored data.
---
## Diagnostic and Internal Bang Commands
Internal diagnostic, manufacturing, and maintenance commands. Sorted lexicographically.
### !! (Double Bang Test)
**Command name:** `!! x dottime maxY numlbls` or `!!`
**Alternative formats:** None
**Parameters (Inputs):**
- `x` - Test pattern parameter (UIntRange)
- `dottime` - Dot timing parameter (UIntRange)
- `maxY` - Maximum Y coordinate (UIntRange)
- `numlbls` - Number of test labels (UIntRange)
**Outputs (Return values):** Test pattern execution results
**Usage examples:**
```
!! 1 10 100 5
END
```
```
!!
```
**Description:** Executes internal test patterns for manufacturing diagnostics; simple form prints test header.
---
### !CAL
**Command name:** `!CAL mode` or `!CAL`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Calibration mode (0-3, 23, 254): 0=Direct Thermal Bar, 1=Direct Thermal Gap, etc.
**Outputs (Return values):** Calibration results
**Usage examples:**
```
!CAL 0
!CAL 254
END
```
```
!CAL
```
**Description:** Calibrates index settings for different feed and print types.
---
### !DUMP DATAFLASH
**Command name:** `!DUMP DATAFLASH`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** DataFlash contents
**Usage examples:**
```
!DUMP DATAFLASH
```
**Description:** Dumps DataFlash memory contents.
---
### !DUMP FACTORYAVARS
**Command name:** `!DUMP FACTORYAVARS`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Factory variables contents
**Usage examples:**
```
!DUMP FACTORYAVARS
END
```
**Description:** Displays contents of factory variable memory area.
---
### !DUMP FACTORYBVARS
**Command name:** `!DUMP FACTORYBVARS`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Factory backup variables contents
**Usage examples:**
```
!DUMP FACTORYBVARS
END
```
**Description:** Displays contents of factory backup variable memory area.
---
### !DUMP IMAGE
**Command name:** `!DUMP IMAGE`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Image buffer contents
**Usage examples:**
```
!DUMP IMAGE
END
```
**Description:** Displays current image buffer contents for debugging.
---
### !DUMP MEMORY
**Command name:** `!DUMP MEMORY size addr` or `!DUMP MEMORY address length`
**Alternative formats:** None
**Parameters (Inputs):**
- `size` or `length` - Number of bytes to dump (UIntRange)
- `addr` or `address` - Starting memory address (hex)
**Outputs (Return values):** Memory contents
**Usage examples:**
```
!DUMP MEMORY 20 0x20123456
END
```
```
!DUMP MEMORY 0x1000 256
```
**Description:** Displays contents of RAM memory for debugging.
---
### !DUMP USERVARS
**Command name:** `!DUMP USERVARS`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** User variables contents
**Usage examples:**
```
!DUMP USERVARS
END
```
**Description:** Displays contents of user variable memory area.
---
### !ERASE EVENTLOG
**Command name:** `!ERASE EVENTLOG`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Erase operation results
**Usage examples:**
```
!ERASE EVENTLOG
END
```
**Description:** Clears the printer's event log.
---
### !ERASE SECTOR
**Command name:** `!ERASE SECTOR[A|B]` or `!ERASE SECTORA` / `!ERASE SECTORB`
**Alternative formats:** None
**Parameters (Inputs):**
- `SECTOR[A|B]` - Sector to erase (A or B)
**Outputs (Return values):** Erase operation results
**Usage examples:**
```
!ERASE SECTORA
END
```
**Description:** Erases specified flash memory sector for maintenance.
---
### !GET INDEX
**Command name:** `!GET INDEX` or `!QI`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current index settings
**Usage examples:**
```
!GET INDEX
END
```
**Description:** Retrieves current index calibration settings.
---
### !GET SHIFT
**Command name:** `!GET SHIFT`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Shift settings
**Usage examples:**
```
!GET SHIFT
```
**Description:** Retrieves current print shift/offset settings.
---
### !GET TOF
**Command name:** `!GET TOF`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current TOF (Top of Form) setting
**Usage examples:**
```
!GET TOF
END
```
**Description:** Retrieves current Top of Form setting.
---
### !HISTORY DISABLE
**Command name:** `!HISTORY DISABLE`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Disable confirmation
**Usage examples:**
```
!HISTORY DISABLE
```
**Description:** Disables all history tracking.
---
### !HISTORY ENABLE
**Command name:** `!HISTORY ENABLE hexvalue`
**Alternative formats:** None
**Parameters (Inputs):**
- `hexvalue` - History enable mask (hexadecimal)
**Outputs (Return values):** Enable confirmation
**Usage examples:**
```
!HISTORY ENABLE 0xFF
```
**Description:** Enables specific history tracking with bitmask.
---
### !HISTORY INIT
**Command name:** `!HISTORY INIT hexvalue`
**Alternative formats:** None
**Parameters (Inputs):**
- `hexvalue` - History initialization value (hexadecimal)
**Outputs (Return values):** Initialization confirmation
**Usage examples:**
```
!HISTORY INIT 0x00
```
**Description:** Initializes history system with specified parameters.
---
### !HISTORY OUTPUT
**Command name:** `!HISTORY OUTPUT enable/disable`
**Alternative formats:** None
**Parameters (Inputs):**
- `enable/disable` - History output control
**Outputs (Return values):** Output control confirmation
**Usage examples:**
```
!HISTORY OUTPUT ENABLE
```
**Description:** Enables or disables history output logging.
---
### !LOAD INCHCOUNT
**Command name:** `!LOAD INCHCOUNT nnn`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` - Inch count value to load (UIntRange)
**Outputs (Return values):** Load operation results
**Usage examples:**
```
!LOAD INCHCOUNT 1000
END
```
**Description:** Loads inch count value for factory tracking.
---
### !LOAD LICENSE
**Command name:** `!LOAD LICENSE "key"`
**Alternative formats:** None
**Parameters (Inputs):**
- `"key"` - License key string
**Outputs (Return values):** Load confirmation
**Usage examples:**
```
!LOAD LICENSE "ABC123-DEF456-GHI789"
```
**Description:** Loads software license key (manufacturing use).
---
### !LOAD MAC
**Command name:** `!LOAD MAC addr`
**Alternative formats:** None
**Parameters (Inputs):**
- `addr` - MAC address string (e.g., 00:11:22:33:44:55)
**Outputs (Return values):** Load operation results
**Usage examples:**
```
!LOAD MAC 00:11:22:33:44:55
END
```
**Description:** Sets the network interface MAC address.
---
### !LOAD MODELNUMBER
**Command name:** `!LOAD MODELNUMBER model`
**Alternative formats:** None
**Parameters (Inputs):**
- `model` - Model number string
**Outputs (Return values):** Load operation results
**Usage examples:**
```
!LOAD MODELNUMBER BT200
END
```
**Description:** Sets the printer's model number.
---
### !LOAD OOBVARS
**Command name:** `!LOAD OOBVARS`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Load operation results
**Usage examples:**
```
!LOAD OOBVARS
END
```
**Description:** Loads out-of-box variables to factory defaults.
---
### !LOAD SERIALNUMBER
**Command name:** `!LOAD SERIALNUMBER serial`
**Alternative formats:** None
**Parameters (Inputs):**
- `serial` - Serial number string
**Outputs (Return values):** Load operation results
**Usage examples:**
```
!LOAD SERIALNUMBER ABC123456
END
```
**Description:** Sets the printer's serial number.
---
### !PRINT TESTLABEL
**Command name:** `!PRINT TESTLABEL`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Prints test label
**Usage examples:**
```
!PRINT TESTLABEL
```
**Description:** Prints comprehensive printer test label with configuration.
---
### !QIB
**Command name:** `!QIB [I|D]`
**Alternative formats:** None
**Parameters (Inputs):**
- `I` - Initialize buffer (optional)
- `D` - Dump buffer (optional, default)
**Outputs (Return values):** Buffered index sensor values
**Usage examples:**
```
!QIB I
!QIB D
!QIB
```
**Description:** Queries buffered index sensor values log. `!QIB I` initializes buffer, `!QIB D` or `!QIB` dumps the buffer.
---
### !QM
**Command name:** `!QM`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Memory type and size
**Usage examples:**
```
!QM
```
**Description:** Queries memory type and total size information.
---
### !QSA
**Command name:** `!QSA`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Sector architecture
**Usage examples:**
```
!QSA
```
**Description:** Queries flash memory sector architecture details.
---
### !QSTACK
**Command name:** `!QSTACK`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Stack status
**Usage examples:**
```
!QSTACK
```
**Description:** Queries current stack usage and status.
---
### !SET HOST_NAME
**Command name:** `!SET HOST_NAME "hostname"` or `!SET HOSTNAME hostname`
**Alternative formats:** None
**Parameters (Inputs):**
- `"hostname"` - Host name string
**Outputs (Return values):** Hostname setting confirmation
**Usage examples:**
```
!SET HOST_NAME "PRINTER001"
END
```
```
!SET HOSTNAME PRINTER001
STRING 12x16 10 10 Hostname Set
END
```
**Description:** Sets network host name for the printer.
---
### !SET INDEX
**Command name:** `!SET INDEX "FeedType" "PrintMode" "R%" "T%" "Gain" "WhiteLevel"`
**Alternative formats:** None
**Parameters (Inputs):**
- `"FeedType"` - Media feed type (e.g., GAP)
- `"PrintMode"` - Print mode (e.g., DIRECT)
- `"R%"` - Reflective LED percentage (UIntRange)
- `"T%"` - Transmissive LED percentage (UIntRange)
- `"Gain"` - Sensor gain value (UIntRange)
- `"WhiteLevel"` - White level threshold (UIntRange)
**Outputs (Return values):** Index configuration confirmation
**Usage examples:**
```
!SET INDEX GAP DIRECT 50 50 100 200
```
**Description:** Sets comprehensive index sensor parameters.
---
### !SET OEMIDENTIFIER
**Command name:** `!SET OEMIDENTIFIER identifier`
**Alternative formats:** None
**Parameters (Inputs):**
- `identifier` - OEM identifier string
**Outputs (Return values):** Set confirmation
**Usage examples:**
```
!SET OEMIDENTIFIER CUSTOM_OEM
```
**Description:** Sets OEM identifier for custom branding (manufacturing use).
---
### !SET OEMMODELID
**Command name:** `!SET OEMMODELID modelid`
**Alternative formats:** None
**Parameters (Inputs):**
- `modelid` - OEM model identifier (string or number)
**Outputs (Return values):** Set confirmation
**Usage examples:**
```
!SET OEMMODELID 12345
```
**Description:** Sets OEM model ID (manufacturing use).
---
### !SET PEELER
**Command name:** `!SET PEELER mode`
**Alternative formats:** None
**Parameters (Inputs):**
- `mode` - Peeler configuration mode (e.g., ENABLED)
**Outputs (Return values):** Configuration confirmation
**Usage examples:**
```
!SET PEELER ENABLED
```
**Description:** Configures label peeler operation mode.
---
### !SET PRINTHEAD
**Command name:** `!SET PRINTHEAD parameters`
**Alternative formats:** None
**Parameters (Inputs):**
- `parameters` - Print head configuration parameters (e.g., 203,832 for DPI,width)
**Outputs (Return values):** Configuration confirmation
**Usage examples:**
```
!SET PRINTHEAD 203,832
```
**Description:** Configures print head specifications (manufacturing use).
---
### !SET SHIFT
**Command name:** `!SET SHIFT value`
**Alternative formats:** None
**Parameters (Inputs):**
- `value` - Print shift/offset value (IntRange)
**Outputs (Return values):** Set confirmation
**Usage examples:**
```
!SET SHIFT 10
```
**Description:** Sets the global print position shift/offset.
---
### !SET TIME
**Command name:** `!SET TIME YYYY MM DD hh mm ss` or `!SET TIME hh:mm:ss`
**Alternative formats:** None
**Parameters (Inputs):**
- `YYYY` - Year (4 digits, 1970-2069)
- `MM` - Month (2 digits, 1-12)
- `DD` - Day (2 digits, 1-31)
- `hh` - Hour (2 digits, 0-23)
- `mm` - Minute (2 digits, 0-59)
- `ss` - Second (2 digits, 0-59)
**Outputs (Return values):** Time setting confirmation
**Usage examples:**
```
!SET TIME 2024 12 25 14 30 00
END
```
```
!SET TIME 14:30:00
```
**Description:** Sets the printer's internal clock.
---
### !SET TOF
**Command name:** `!SET TOF nnn` or `!SET TOF value`
**Alternative formats:** None
**Parameters (Inputs):**
- `nnn` or `value` - TOF value (IntRange)
**Outputs (Return values):** TOF setting confirmation
**Usage examples:**
```
!SET TOF 100
END
```
```
!SET TOF 50
```
**Description:** Sets the Top of Form value.
---
### !SHOW AD
**Command name:** `!SHOW AD`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** A/D converter readings
**Usage examples:**
```
!SHOW AD
END
```
**Description:** Displays analog-to-digital converter readings for diagnostic purposes.
---
### !SHOW EVENTLOG
**Command name:** `!SHOW EVENTLOG`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Event log contents
**Usage examples:**
```
!SHOW EVENTLOG
END
```
**Description:** Displays the printer's event log for troubleshooting.
---
### !SHOW HEAP
**Command name:** `!SHOW HEAP`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Heap memory statistics
**Usage examples:**
```
!SHOW HEAP
```
**Description:** Shows heap memory allocation and usage statistics.
---
### !SHOW HISTORY
**Command name:** `!SHOW HISTORY` or `!QH`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Command history buffer
**Usage examples:**
```
!SHOW HISTORY
END
```
**Description:** Displays the command history buffer for debugging.
---
### !SHOW HOST_NAME
**Command name:** `!SHOW HOST_NAME`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current hostname
**Usage examples:**
```
!SHOW HOST_NAME
```
**Description:** Displays the printer's network hostname.
---
### !SHOW INCHCOUNT
**Command name:** `!SHOW INCHCOUNT`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Total inches printed
**Usage examples:**
```
!SHOW INCHCOUNT
```
**Description:** Shows cumulative count of inches printed.
---
### !SHOW MAC
**Command name:** `!SHOW MAC`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** MAC address
**Usage examples:**
```
!SHOW MAC
```
**Description:** Displays the Ethernet MAC address.
---
### !SHOW MEMORY
**Command name:** `!SHOW MEMORY`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Memory usage information
**Usage examples:**
```
!SHOW MEMORY
END
```
**Description:** Displays current memory usage statistics.
---
### !SHOW MODELNUMBER
**Command name:** `!SHOW MODELNUMBER`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Printer model number
**Usage examples:**
```
!SHOW MODELNUMBER
```
**Description:** Displays the printer model number.
---
### !SHOW OEMIDENTIFIER
**Command name:** `!SHOW OEMIDENTIFIER`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** OEM identifier information
**Usage examples:**
```
!SHOW OEMIDENTIFIER
END
```
**Description:** Displays the current OEM identifier for the printer.
---
### !SHOW OEMMODELID
**Command name:** `!SHOW OEMMODELID`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** OEM model ID information
**Usage examples:**
```
!SHOW OEMMODELID
END
```
**Description:** Displays the OEM model identifier for the printer.
---
### !SHOW PRINTHEAD
**Command name:** `!SHOW PRINTHEAD`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Print head specifications
**Usage examples:**
```
!SHOW PRINTHEAD
```
**Description:** Displays print head type, resolution, and width.
---
### !SHOW SECTORADDR
**Command name:** `!SHOW SECTORADDR` or `!QSA`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Flash sector architecture information
**Usage examples:**
```
!SHOW SECTORADDR
END
```
**Description:** Displays flash memory sector architecture information.
---
### !SHOW SERIALNUMBER
**Command name:** `!SHOW SERIALNUMBER`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Printer serial number
**Usage examples:**
```
!SHOW SERIALNUMBER
```
**Description:** Displays the printer serial number.
---
### !SHOW STACK
**Command name:** `!SHOW STACK` or `!QSTACK`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Stack status information
**Usage examples:**
```
!SHOW STACK
END
```
**Description:** Displays current stack status and usage information.
---
### !SET OOBVARS
**Command name:** `!SET OOBVARS parameters`
**Alternative formats:** None
**Parameters (Inputs):**
- `parameters` - Out-of-box variable parameters (e.g., DEFAULT)
**Outputs (Return values):** Set confirmation
**Usage examples:**
```
!SET OOBVARS DEFAULT
```
**Description:** Sets out-of-box configuration variables (manufacturing).
---
### TRACE COMMANDS
**Command name:** Various trace control commands (DATA_TRACE_OPEN, etc.)
**Alternative formats:** Multiple trace functions
**Parameters (Inputs):** Trace parameters (e.g., open/close)
**Outputs (Return values):** Trace operation results
**Commands Include:**
- `DATA_TRACE_OPEN` - Open data trace
- `DATA_TRACE_CLOSE` - Close data trace
- `MESSAGE_TRACE_OPEN` - Open message trace
- `MESSAGE_TRACE_CLOSE` - Close message trace
- `TRACE_MARK` - Add trace marker
- `TRACE_FLUSH` - Flush trace buffer
**Usage examples:**
```
DATA_TRACE_OPEN
[commands to trace]
DATA_TRACE_CLOSE
END
```
**Description:** Comprehensive tracing system for debugging and analysis.
---
## Menu and User Interface Commands
Commands for menu systems and user interaction. Sorted lexicographically.
### MENU ACTION
**Command name:** `MENU ACTION "..."` or `MU ACTION "..."`
**Alternative formats:** `MENU ACTION itemname action` (extended)
**Parameters (Inputs):**
- `"..."` - Action command string
- `itemname` (extended) - Menu item identifier
- `action` (extended) - Action to perform when selected (e.g., PRINT)
**Outputs (Return values):** Menu action execution
**Usage examples:**
```
MENU START
MENU ACTION "PRINT_LABEL"
MENU END
END
```
```
MENU ACTION Option1 PRINT
MENU ACTION Option2 FEED
END
```
**Description:** Defines an action to be executed when menu item is selected.
---
### MENU CONTROL
**Command name:** `MENU CONTROL can nxt prv sel` or `MU CONTROL can nxt prv sel`
**Alternative formats:** `MENU CONTROL option` (extended)
**Parameters (Inputs):**
- `can` - Cancel key (CtrlKeyRange)
- `nxt` - Next key (CtrlKeyRange)
- `prv` - Previous key (CtrlKeyRange)
- `sel` - Select key (CtrlKeyRange)
- `option` (extended) - Control option (SHOW/HIDE/ENABLE/DISABLE)
**Outputs (Return values):** Menu control key mapping
**Usage examples:**
```
MENU CONTROL ESC PGDN PGUP ENTER
MENU START
MENU ITEM Option1
MENU END
END
```
```
MENU CONTROL SHOW
END
```
**Description:** Defines control key mappings for menu navigation or controls visibility/interaction.
---
### MENU END
**Command name:** `MENU END` or `MU END`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (menu system)
**Usage examples:**
```
MENU START
MENU ITEM Test Option
MENU END
END
```
**Description:** Terminates menu system definition.
---
### MENU EXIT
**Command name:** `MENU EXIT` or `MU EXIT`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** None (menu system)
**Usage examples:**
```
MENU START
MENU ITEM Option1
MENU EXIT
END
```
**Description:** Defines menu exit point or action; exits current menu level.
---
### MENU ITEM
**Command name:** `MENU ITEM label` or `MU ITEM "...."`
**Alternative formats:** `MENU ITEM itemname "display text"` (extended)
**Parameters (Inputs):**
- `label` - Menu item label
- `"...."` - Menu item text (up to 4 characters)
- `itemname` (extended) - Unique item identifier
- `"display text"` (extended) - Text displayed in menu
**Outputs (Return values):** None (menu system)
**Usage examples:**
```
MENU START
MENU ITEM Print Label
MENU ITEM Configure
MENU END
END
```
```
MENU START
MENU ITEM "Opt1"
MENU ITEM "Opt2"
MENU END
END
```
```
MENU ITEM PrintLabel "Print Test Label"
MENU ITEM Configure "Configuration Menu"
END
```
**Description:** Defines a menu item in the menu system.
---
### MENU MESSAGE
**Command name:** `MENU MESSAGE "text"`
**Alternative formats:** None
**Parameters (Inputs):**
- `"text"` - Message text to display
**Outputs (Return values):** None (message display)
**Usage examples:**
```
MENU MESSAGE "Please select an option"
END
```
**Description:** Displays a message within the menu system.
---
### MENU START
**Command name:** `MENU START` or `MENU START menuname`
**Alternative formats:** `!S~ 3 F/E`
**Parameters (Inputs):**
- Storage location and format parameters
- `menuname` (extended) - Unique menu identifier
**Outputs (Return values):** None (menu system)
**Usage examples:**
```
MENU START
MENU ITEM Option1
MENU END
END
```
```
MENU START MainMenu
MENU ITEM Option1 "First Option"
MENU ITEM Option2 "Second Option"
MENU END
END
```
**Description:** Initiates menu system for user interaction.
---
### RECALL MENU
**Command name:** `RECALL MENU menuname`
**Alternative formats:** None
**Parameters (Inputs):**
- `menuname` - Name of menu to display
**Outputs (Return values):** None (menu activation)
**Usage examples:**
```
RECALL MENU MainMenu
END
```
**Description:** Activates and displays a previously defined menu.
---
## Network and Communication Commands
Commands for network setup and protocols. Sorted lexicographically.
### SET HOST NAME
**Command name:** `!SET HOSTNAME hostname`
**Alternative formats:** See !SET HOST_NAME
**Parameters (Inputs):** See !SET HOST_NAME
**Outputs (Return values):** See !SET HOST_NAME
**Usage examples:** See !SET HOST_NAME
**Description:** Alias for setting hostname.
---
## Bluetooth Commands
Bluetooth-specific configuration. Sorted lexicographically.
### BLUETOOTH COMMANDS (Complete Set)
**Command name:** Various Bluetooth configuration functions
**Alternative formats:** Multiple specific commands (see VARIABLE BLUETOOTH subcommands)
**Parameters (Inputs):** Various Bluetooth parameters
**Outputs (Return values):** Bluetooth operation results
**Commands Include:**
- See VARIABLE BLUETOOTH subcommands above
**Usage examples:**
```
BLUETOOTH_DISCOVERABLE ON
STRING 12x16 10 10 Bluetooth Configured
END
```
```
BLUETOOTH_DEVICE_NAME "My Printer"
BLUETOOTH_DISCOVERABLE ON
BLUETOOTH_SECURITY 2
END
```
**Description:** Configure various Bluetooth settings including discoverability, security, encryption, etc. (complete suite via VARIABLE BLUETOOTH).
---
## Time and Date Commands
Commands for time management. Sorted lexicographically.
### TIME ADD
**Command name:** `TIME ADD YY MM DD hh mm ss`
**Alternative formats:** None
**Parameters (Inputs):**
- `YY` - Years to add (UIntRange)
- `MM` - Months to add (UIntRange)
- `DD` - Days to add (UIntRange)
- `hh` - Hours to add (UIntRange)
- `mm` - Minutes to add (UIntRange)
- `ss` - Seconds to add (UIntRange)
**Outputs (Return values):** Time addition results
**Usage examples:**
```
TIME ADD 0 1 0 0 0 0
STRING 12x16 10 10 Added One Month
END
```
**Description:** Adds specified time to current time.
---
### TIME GET
**Command name:** `TIME GET`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current time
**Usage examples:**
```
TIME GET
STRING 12x16 10 10 Current Time Retrieved
END
```
**Description:** Retrieves current printer time.
---
### TIME QUERY
**Command name:** `TIME ?` or `!QT`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current time and date
**Usage examples:**
```
TIME ?
STRING 12x16 10 10 Time Queried
END
```
**Description:** Queries current time and date.
---
### TIME SET
**Command name:** `TIME SET YYYY MM DD hh mm ss` or `!SET TIME YYYY MM DD hh mm ss`
**Alternative formats:** None
**Parameters (Inputs):**
- `YYYY` - Year (1970-2069)
- `MM` - Month (1-12)
- `DD` - Day (1-31)
- `hh` - Hour (0-23)
- `mm` - Minute (0-59)
- `ss` - Second (0-59)
**Outputs (Return values):** Time setting confirmation
**Usage examples:**
```
TIME SET 2024 12 25 14 30 00
STRING 12x16 10 10 Time Set
END
```
**Description:** Sets the printer's internal clock.
---
## Query Commands
General query commands for system info. Sorted lexicographically.
### !QB
**Command name:** `!QB`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Bootcode revision
**Usage examples:**
```
!QB
```
**Description:** Queries the bootloader firmware revision.
---
### !QD
**Command name:** `!QD`
**Alternative formats:** None
**Parameters (Inputs):** None
**Outputs (Return values):** Current date
**Usage examples:**
```
!QD
```
**Description:** Queries the printer's current date setting.
---
### !QH
**Command name:** `!QH`
**Alternative formats:** See !SHOW HISTORY
**Parameters (Inputs):** None
**Outputs (Return values):** Command history
**Usage examples:**
```
!QH
```
**Description:** Queries the command history log.
---
### !QI
**Command name:** `!QI`
**Alternative formats:** See !GET INDEX
**Parameters (Inputs):** None
**Outputs (Return values):** Index settings
**Usage examples:**
```
!QI
```
**Description:** Queries current index sensor settings.
---
### !QT
**Command name:** `!QT`
**Alternative formats:** See TIME QUERY
**Parameters (Inputs):** None
**Outputs (Return values):** Current time
**Usage examples:**
```
!QT
```
**Description:** Queries the printer's current time setting.
---
## Command Categories Summary
This unified guide consolidates all CPL commands into logical categories, eliminating duplicates and ensuring completeness. Total unique commands: ~250 (including variants).
## Cross-References
- **Pairs:** ADJUST ↔ ADJUST_DUP; STORE_* ↔ RECALL_*; HEADER variants (HEADER, HEADER_PLUS, HEADER_AUTO, BACKGROUND HEADER)
- **Complements:** DRAW_* ↔ FILL_*; Time: TIME SET ↔ TIME GET ↔ TIME ADD ↔ TIME QUERY
- **Network:** IPADDR/NETMASK/GATEWAY/HOSTNAME; VARIABLE WRITE for persistence
- **Security:** PASSWORD HEADER; Bluetooth security levels
## Notes
- All label formats end with END (or variants) to execute.
- Coordinates in printer dots; ranges model-dependent.
- Bang (!) commands often require privileged access.
- Save with VARIABLE WRITE for NVM.
- Consult manufacturer docs for model variations.