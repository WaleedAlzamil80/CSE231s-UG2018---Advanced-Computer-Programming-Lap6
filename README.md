# Exception Handling Assignment

Write a program that reads an ARXML file containing a list of containers, each with a unique ID, 
and reorders the containers alphabetically by their name sub- container “"SHORT-NAME"”.

The program should write the reordered containers to a new ARXML file.

ARXML stands for Autosar xml

Autosar stands for AUTomotive Open System ARchitecture

## Requirements:
- 1- The name of the arxml file shall be an argument which needs to passed through the command line.

- 2- If the file is not having .arxml extension then you should trigger a user defined handled 
exception “NotVaildAutosarFileException”.

- 3- If the file is empty, then you should trigger user defined unhandled exception “EmptyAutosarFileException”

- 4- The output file shall be named as the same of the input file concatenated with “_mod.arxml” 
• e.g. if the input was named “Rte_Ecuc.arxml” then the output should be “Rte_Ecuc_mod.arxml”.

- 5- Assume any missing requirement.
