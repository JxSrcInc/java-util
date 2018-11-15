This package is an Action - do something on the file, but add it in filter chain
not implement as Action interface.

This allows one operation on a file can depend on other operations on the same file. 
For example, ModifyFilter will first modify a file if the file content contains some specific
String, then a SaveFilter can save the modified content

See jxsource.util.folder.manager.ModifyManager for how to use