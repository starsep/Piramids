Android game Piramids
=====================

Idea came from Piramids game from http://www.wydawnictwologi.pl/

Version convention
==================

Application version is database version divided by 100.

```java
float applicationVersion = DatabaseManager.DATABASE_VERSION / 100f;
```

Examples:
---------

```java
if(DATABASE_VERSION == 1)
	applicationVersion = 0.01f;
if(DATABASE_VERSION == 42)
	applicationVersion = 0.42f;
if(DATABASE_VERSION == 123)
	applicationVersion = 1.23f;
if(DATABASE_VERSION == 314)
	applicationVersion = 3.14f;
```

Versions:
=========

0.04
----
-some stuff needed to publish app @ Google Play

0.03
----
-actual game in database now  
-continue game option working  

0.02
----
-same/another/error values coloring  
-number buttons works  

0.01
----

-simple UI  
-generating full levels  
-simple Sqlite database  

Authors
======

Filip <starsep> Czaplicki filipczaplicki@gmail.com
