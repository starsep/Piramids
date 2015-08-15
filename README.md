Android game Piramids
=====================

Idea came from Piramids game from http://www.wydawnictwologi.pl/

Version convention
==================

Version convention is that application version is database version divided by 100.

	:::java
	float applicationVersion = DatabaseManager.DATABASE_VERSION / 100f;

Versions:
=========

0.01
----

-simple UI  
-generating full levels  
-simple Sqlite database  

Examples:
---------

	:::java
	if(DATABASE_VERSION == 1)
		applicationVersion = 0.01f;
	if(DATABASE_VERSION == 42)
		applicationVersion = 0.42f;
	if(DATABASE_VERSION == 123)
		applicationVersion = 1.23f;
	if(DATABASE_VERSION == 314)
		applicationVersion = 3.14f;

Authors
======

Filip <starsep> Czaplicki filipczaplicki@gmail.com
