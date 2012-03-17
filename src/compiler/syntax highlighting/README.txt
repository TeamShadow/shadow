To add syntax highlighting for Shadow to Eclipse, do the following:

1.	Copy the cbg.editor_1.2.6.jar file into your Eclipse plugins directory.
2.	Go into Eclipse preferences: General -> Editors -> File Associations and add *.shadow
	to the list with the Syntax Coloring Editor as the associated editor.
3.	Change the default colors by going to preferences: Coloring Editor -> Colors.
	(The default colors are horrible)
	
Eventually, we'll need a better tool for syntax highlighting.  This tool is just a regular
expression matcher.

The Excel spreadsheet is only a way of keeping track of changes to the keywords and has no
real importance.