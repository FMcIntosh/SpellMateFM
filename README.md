## SpellMate
SpellMate is a spelling aid that tests your spelling by speaking words out loud, which you can type and check to see if your spelling was correct. It has several modes:
- Quiz mode, which tests 3 words from the wordlist.txt file. 
- Review mode, tests 3 words that have been failed
- Statistics: see how many words have been faulted, failed or mastered
- Clear History: Clears all history from the app

### To run SpellMate:
**TerminaL:** in the terminal navigate to the 'SpellMate' directory, and run the command 'bash SpellMate.sh'.

**Double-click:** Double-click on SpellMate.sh. First ensure that the File Manager preferences are set to allow executables to run when clicked.

To change the words in the wordlist file, simply edit or update the file. Ensure that it is named wordlist.txt WITH the .txt extension, and that it is kept in the 'SpellMate'directory

**Note**
This application is targeted at Java 1.7 due to the requirements of the project. There may be issues running it with 1.8. Play around with the classpath in the script file, and maybe remove the javaFX library from it if you are having issues

Also, SpellMate is made for a linux environment that has the Festival tts package installed.
