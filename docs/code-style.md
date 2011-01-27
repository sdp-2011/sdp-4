Code Style
==========

Java
----

All of our Java code is formatted with astyle. The command to get a similar formatting is:

    astyle -r --mode=java --style=allman --indent=tab --indent-switches \
		--pad-oper --pad-header --unpad-paren --delete-empty-lines \
		--add-brackets src/*

Python
------

Our Python code is 100% PEP-8 compliant. You can install a tool to check this with:

    pip install pep8

It can then be checked with:

    pep8 source.py
