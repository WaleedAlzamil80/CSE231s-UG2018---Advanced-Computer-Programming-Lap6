@echo off

REM Compile the Java file
javac -d . ArxmlSorter.java

REM Normal case
echo Testing normal case...
java Lap6.ArxmlSorter Normal.arxml
echo ----------------------------------------------------------------------------------
REM Not valid Autosar file case
echo Testing not valid Autosar file case...
java Lap6.ArxmlSorter Report_Lap_6.docs
echo ----------------------------------------------------------------------------------
REM Empty file case
echo Testing empty file case...
java Lap6.ArxmlSorter Empty.arxml
echo ----------------------------------------------------------------------------------

REM Clean up compiled Java files
del Lap6\*.class

pause