###############################################################################
######################## INB370 2014 Assignment 1 Verifier ####################
###############################################################################

This program tests a submission .zip file for:
	1) directory structure - the right files in the right place.
	2) interface violations - ensures the solution follows the provided 
		interface, including the specified constructors.
	3) test violations - ensures the tests do not access any public 
		variables/functions not defined in the interface.

A folder "temp" is created but is not removed afterwards. Delete this.

################################### INSTRUCTIONS ###############################

To use the provided "Run A1 Verify.bat", you must copy the .jar file and the 
.bat file to "Programs" folder that contains the portable version of OpenJDK that
you downloaded from Blackboard. You must also copy your submission zip file
which must be called "submission.zip" into the same directory. Then run "Run
A1 Verify.bat" and the results will be output to "submission_verification_results.txt".

################################## SIDE NOTE ###################################

Usage: java -jar verify_inb370_ass1_2014.jar [PATH_TO_SUBMISSION_ZIP] [OUTPUT_FILENAME]

PATH_TO_SUBMISSION_ZIP defaults to "submission.zip".
The output is written to OUTPUT_FILENAME or stdout if not present.

The program must be run with JDK's java executables (because it requires the 
Java compiler), so you must first prepend the path to JDK's bin folder to the 
PATH environment variable. 

For example, in Windows (assuming the current directory is the downloaded 
Programs folder that contains JDK):
	set PATH=.\OpenJDK\bin;%PATH%	
Or some Unix derivative:
	export PATH=.\OpenJDK\bin:$PATH
Then run with (assuming in the current directory as the .jar and .zip):
	java -jar verify_inb370_ass1_2014.jar submission.zip

