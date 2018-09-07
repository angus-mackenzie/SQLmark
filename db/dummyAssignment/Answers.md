# Query 1 Table 1

SELECT * FROM Matric WHERE StuID = "1001";

```
mysql> SELECT * FROM Matric WHERE StuID = "1001";
+-------+--------+-----------+----------+--------+--------------------+-------------------+------------+------+--------------------+-----------+----------+
| StuID | Prefix | FirstName | LastName | Gender | Citizenship Status | SchoolName        | SchoolCode | Year | ExaminingAuthority | OldExAuth | UCTScore |
+-------+--------+-----------+----------+--------+--------------------+-------------------+------------+------+--------------------+-----------+----------+
|  1001 | Mr     | Aaron     | Zwillig  | M      | C                  | Private Candidate |    1004249 | 2007 |                    | TR        |       34 |
+-------+--------+-----------+----------+--------+--------------------+-------------------+------------+------+--------------------+-----------+----------+
1 row in set (0.00 sec)
```
# Query 1 Table 2

SELECT * FROM uniData WHERE StuID = "1001";
```
mysql> SELECT * FROM uniData WHERE StuID = "1001";
+-------+--------+---------+--------+---------+--------+---------+
| StuID | Course | Percent | Symbol | Credits | Senior | Science |
+-------+--------+---------+--------+---------+--------+---------+
| 1001  | ACCS1  |      61 | 2-     |      18 | N      | N       |
| 1001  | CS1    |      88 | 1      |      18 | N      | Y       |
| 1001  | ECO1   |      66 | 2-     |      18 | N      | N       |
| 1001  | MAM1   |       0 | AB     |      18 | N      | Y       |
| 1001  | PSY1   |      58 | 3      |      36 | N      | N       |
| 1001  | STA1   |      58 | 3      |      18 | N      | Y       |
+-------+--------+---------+--------+---------+--------+---------+
6 rows in set (0.00 sec)
```
# Query 2 Table 1

SELECT COUNT(*) FROM Matric;
```

mysql> SELECT COUNT(*) FROM Matric;
+----------+
| COUNT(*) |
+----------+
|      521 |
+----------+
1 row in set (0.00 sec)
```

# Query 2 Table 2

SELECT COUNT(*) FROM uniData;

mysql> SELECT COUNT(*) FROM uniData;
```
+----------+
| COUNT(*) |
+----------+
|      521 |
+----------+
1 row in set (0.00 sec)

```
# Query 3

SELECT * FROM Matric WHERE (Gender = 'F' AND Prefix = "Mr") OR (Gender = 'M' and (Prefix = "Miss" OR Prefix = "Ms"));
```
mysql> SELECT * FROM Matric WHERE (Gender = 'F' AND Prefix = "Mr") OR (Gender = 'M' and (Prefix = "Miss" OR Prefix = "Ms"));
+-------+--------+-----------+------------+--------+--------------------+------------------------+------------+------+--------------------+-----------+----------+
| StuID | Prefix | FirstName | LastName   | Gender | Citizenship Status | SchoolName             | SchoolCode | Year | ExaminingAuthority | OldExAuth | UCTScore |
+-------+--------+-----------+------------+--------+--------------------+------------------------+------------+------+--------------------+-----------+----------+
|  1439 | Ms     | Sifiso    | Culverwell | M      | F                  | Eaglesvale High School |    1001183 | 2004 | CL                 |           |        0 |
+-------+--------+-----------+------------+--------+--------------------+------------------------+------------+------+--------------------+-----------+----------+
1 row in set (0.00 sec)
```
# Query 4

SELECT FirstName, LastName FROM Matric WHERE SchoolCode = 1000929 ORDER BY LastName;
```
mysql> SELECT FirstName, LastName FROM Matric WHERE SchoolCode = 1000929 ORDER BY LastName;
+-----------+----------+
| FirstName | LastName |
+-----------+----------+
| Sophy     | Cooper   |
| Beth      | Turner   |
+-----------+----------+
2 rows in set (0.00 sec)
```

# Query 5

SELECT Course, round((Credits/6)) AS numberOfUnits FROM uniData GROUP BY COURSE;
```
mysql> SELECT Course, round((Credits/6)) AS numberOfUnits FROM uniData GROUP BY COURSE;
+--------+---------------+
| Course | numberOfUnits |
+--------+---------------+
| ACCS1  |             3 |
| ACCS2  |             3 |
| ACCS3  |             3 |
| BUS1   |             3 |
| BUS3   |             3 |
| CS1    |             3 |
| CS2    |             4 |
| CS3    |             6 |
| ECO1   |             3 |
| ECO2   |             3 |
| ECO3   |             3 |
| GAM1   |             4 |
| GAM2   |             6 |
| MAM1   |             3 |
| MAM2   |             4 |
| MAM3   |             6 |
| PSY1   |             6 |
| PSY2   |             4 |
| STA1   |             3 |
| STA2   |             4 |
| STA3   |             6 |
| ZOO1   |             3 |
| ZOO3   |             6 |
+--------+---------------+
23 rows in set (0.01 sec)
```
# Query 6

SELECT FirstName, LastName FROM Matric WHERE `Citizenship Status` = 'C' AND ExaminingAuthority = "" AND OldExAuth = "" AND UCTScore = 0;
```
mysql> SELECT FirstName, LastName FROM Matric WHERE `Citizenship Status` = 'C' AND ExaminingAuthority = "" AND OldExAuth = "" AND UCTScore = 0;
+-----------+----------+
| FirstName | LastName |
+-----------+----------+
| Lauren    | Masendu  |
| Thulani   | Baxter   |
+-----------+----------+
2 rows in set (0.00 sec)
```
# Query 7

SELECT StuID FROM uniData WHERE Course = "ZOO3" AND StuID IN (SELECT StuID FROM uniData WHERE Course = "ACCS1");
```
mysql> SELECT StuID FROM uniData WHERE Course = "ZOO3" AND StuID IN (SELECT StuID FROM uniData WHERE Course = "ACCS1");
+-------+
| StuID |
+-------+
| 1006  |
| 1012  |
| 1023  |
| 1029  |
| 1040  |
| 1046  |
| 1057  |
| 1063  |
| 1082  |
+-------+
9 rows in set (0.01 sec)
```
# Query 8

SELECT StuID, Course, MAX(Percent) AS Highest FROM uniData;
```
mysql> SELECT StuID, Course, MAX(Percent) AS Highest FROM uniData;
+-------+--------+---------+
| StuID | Course | Highest |
+-------+--------+---------+
| 1001  | ACCS1  |      88 |
+-------+--------+---------+
1 row in set (0.00 sec)
```
# Query 9

The question was "How many students have a mark (in any course) lower than some mark that student 1027 achieved?"

For the some mark I decided to use his minimum mark for the query.


SELECT COUNT(DISTINCT(StuID)) FROM uniData WHERE Percent < (SELECT MIN(Percent) FROM uniData WHERE StuID = 1027); 
```
mysql> SELECT COUNT(DISTINCT(StuID)) FROM uniData WHERE Percent < (SELECT MIN(Percent) FROM uniData WHERE StuID = 1027); 
+------------------------+
| COUNT(DISTINCT(StuID)) |
+------------------------+
|                     11 |
+------------------------+
1 row in set (0.00 sec)
```
# Query 10

SELECT distinct(Symbol) FROM uniData;
```
mysql> SELECT distinct(Symbol) FROM uniData;
+--------+
| Symbol |
+--------+
| 2-     |
| 1      |
| AB     |
| 3      |
| 2+     |
| F      |
| DPR    |
+--------+
7 rows in set (0.00 sec)
```
# Query 11

SELECT avg(Percent) FROM uniData;
```
mysql> SELECT avg(Percent) FROM uniData;
+--------------+
| avg(Percent) |
+--------------+
|      61.7140 |
+--------------+
1 row in set (0.00 sec)
```

# Query 12

SELECT Course, min(Percent),max(Percent) FROM uniData WHERE Course LIKE "CS_" GROUP BY Course;
```
mysql> SELECT Course, min(Percent),max(Percent) FROM uniData WHERE Course LIKE "CS_" GROUP BY Course;
+--------+--------------+--------------+
| Course | min(Percent) | max(Percent) |
+--------+--------------+--------------+
| CS1    |            0 |           88 |
| CS2    |            0 |           75 |
| CS3    |           40 |           75 |
+--------+--------------+--------------+
3 rows in set (0.00 sec)
```
# Query 13

SELECT MAX(UCTScore),Year FROM Matric GROUP BY Year HAVING COUNT(Year) >10;
```
mysql> SELECT MAX(UCTScore),Year FROM Matric GROUP BY Year HAVING COUNT(Year) >10;
+---------------+------+
| MAX(UCTScore) | Year |
+---------------+------+
|            46 | 2003 |
|            50 | 2004 |
|            48 | 2005 |
|            50 | 2006 |
+---------------+------+
4 rows in set (0.00 sec)
```
# Query 14 

SELECT DISTINCT Course, COUNT(Course) AS Fails FROM uniData WHERE Symbol = "F" GROUP BY Course HAVING Fails<=(min(Fails)+1) ORDER BY Fails;
```
mysql> SELECT DISTINCT Course, COUNT(Course) AS fails FROM uniData WHERE Symbol = "F" GROUP BY Course 
    -> HAVING Fails<=(min(Fails)+1)
    -> ORDER BY Fails;
+--------+-------+
| Course | fails |
+--------+-------+
| MAM2   |     1 |
| ACCS3  |     1 |
| STA3   |     1 |
| CS1    |     1 |
| CS3    |     1 |
| ACCS2  |     1 |
| ECO1   |     1 |
| PSY1   |     1 |
+--------+-------+
8 rows in set (0.00 sec)
```

# Query 15

SELECT StuID, COUNT(Course) FROM uniData WHERE StuID IN (SELECT StuID FROM uniData WHERE Course = "PSY2") AND Science = 'Y' AND Senior = 'Y' AND Percent > 49 GROUP BY StuID;
```
mysql> SELECT StuID, COUNT(Course) FROM uniData WHERE StuID IN (SELECT StuID FROM uniData WHERE Course = "PSY2") AND Science = 'Y' AND Senior = 'Y' AND Percent > 49 GROUP BY StuID;
+-------+---------------+
| StuID | COUNT(Course) |
+-------+---------------+
| 1004  |             3 |
| 1010  |             3 |
| 1016  |             4 |
| 1021  |             2 |
| 1027  |             2 |
| 1033  |             5 |
| 1038  |             3 |
| 1044  |             3 |
| 1050  |             5 |
| 1055  |             2 |
| 1061  |             3 |
| 1067  |             4 |
| 1071  |             3 |
| 1076  |             5 |
| 1080  |             3 |
| 1086  |             4 |
+-------+---------------+
16 rows in set (0.09 sec)
```
# Query 16

This query derives the number of male and female students and then expresses as a percentage of the total attendees for each school.
This is correct because I calculated the data for Abbotts College - Durbanville and the calculations matched what I found from the SQL query made.

SELECT SchoolName, Total_Students, Female_Students, Male_Students, Round(Female_Students/Total_Students*100) AS Percent_Female, Round(Male_Students/Total_Students*100) AS Percent_Male FROM 
(SELECT SchoolName, Count(Gender) AS Total_Students, Count(Case Gender WHEN 'F' then 1 else null end) AS Female_Students,Count(Case Gender WHEN 'M' then 1 else null end) AS Male_Students  FROM Matric GROUP BY SchoolName) temp;

```
mysql> SELECT SchoolName, Total_Students, Female_Students, Male_Students, Round(Female_Students/Total_Students*100) AS Percent_Female, Round(Male_Students/Total_Students*100) AS Percent_Male FROM 
    -> (SELECT SchoolName, Count(Gender) AS Total_Students, Count(Case Gender WHEN 'F' then 1 else null end) AS Female_Students,Count(Case Gender WHEN 'M' then 1 else null end) AS Male_Students  FROM Matric GROUP BY SchoolName) temp;
+--------------------------------+----------------+-----------------+---------------+----------------+--------------+
| SchoolName                     | Total_Students | Female_Students | Male_Students | Percent_Female | Percent_Male |
+--------------------------------+----------------+-----------------+---------------+----------------+--------------+
| Abbotts College - Claremont    |              5 |               5 |             0 |            100 |            0 |
| Abbotts College - Durbanville  |              3 |               1 |             2 |             33 |           67 |
| Abbotts College - Milnerton    |              1 |               0 |             1 |              0 |          100 |
| Alexander Road High School     |              1 |               1 |             0 |            100 |            0 |
| Amadlelo Aluhlaza Secondary Sc |              1 |               0 |             1 |              0 |          100 |
| Banzana High School            |              1 |               1 |             0 |            100 |            0 |
| Basa Tutorial Institute        |              1 |               1 |             0 |            100 |            0 |
| Belgravia Senior Secondary Sch |              1 |               1 |             0 |            100 |            0 |
| Bergvliet High School          |             10 |               9 |             1 |             90 |           10 |
| Bhukulani Jr Secondary State S |              1 |               1 |             0 |            100 |            0 |
| Bracken High School            |              1 |               0 |             1 |              0 |          100 |
| Bryanston High School          |              1 |               1 |             0 |            100 |            0 |
| Camps Bay High School          |              6 |               4 |             2 |             67 |           33 |
| Cape Town High School          |              1 |               0 |             1 |              0 |          100 |
| Carter High School             |              2 |               0 |             2 |              0 |          100 |
| Chaplin School                 |              1 |               1 |             0 |            100 |            0 |
| Chisipite Senior School        |              1 |               1 |             0 |            100 |            0 |
| Christian Brothers College(Blo |              1 |               1 |             0 |            100 |            0 |
| Christian Brothers College(Spr |              1 |               1 |             0 |            100 |            0 |
| Clarendon Girls High School    |              5 |               3 |             2 |             60 |           40 |
| Collegiate Girls High School   |              4 |               3 |             1 |             75 |           25 |
| Cornwall Hill College          |              1 |               1 |             0 |            100 |            0 |
| Cosat-cnt Of Sciene & Technolo |              1 |               1 |             0 |            100 |            0 |
| Crawford College               |              3 |               1 |             2 |             33 |           67 |
| Crawford College (La Lucia)    |              1 |               1 |             0 |            100 |            0 |
| Crawford College - Durban      |              2 |               1 |             1 |             50 |           50 |
| Crawford College - Pretoria    |              1 |               1 |             0 |            100 |            0 |
| Crawford College Lonehill      |              1 |               1 |             0 |            100 |            0 |
| Crawford North Coast College   |              3 |               3 |             0 |            100 |            0 |
| Creston College                |              1 |               1 |             0 |            100 |            0 |
| Dainfern College               |              1 |               1 |             0 |            100 |            0 |
| Damelin (Correspondence) Colle |              5 |               2 |             3 |             40 |           60 |
| Danville Park Girls High Schoo |              1 |               1 |             0 |            100 |            0 |
| Deutsche Schule Pretoria       |              1 |               1 |             0 |            100 |            0 |
| Df Malan High School - Bellvil |              3 |               1 |             2 |             33 |           67 |
| Diocesan College (Bishops)     |              6 |               5 |             1 |             83 |           17 |
| Diocesan School For Girls      |              3 |               3 |             0 |            100 |            0 |
| Dominican Convent H S (Bulaway |              1 |               0 |             1 |              0 |          100 |
| Dominican Convent High (Harare |              5 |               4 |             1 |             80 |           20 |
| Drakensberg High School        |              1 |               0 |             1 |              0 |          100 |
| Durban Girls` College          |              3 |               3 |             0 |            100 |            0 |
| Eaglesvale High School         |              3 |               2 |             1 |             67 |           33 |
| Edgemead High School           |              8 |               5 |             3 |             63 |           38 |
| Edu-care College               |              2 |               1 |             1 |             50 |           50 |
| Ekangala Comprehensive High Sc |              1 |               1 |             0 |            100 |            0 |
| Embizweni High School          |              1 |               1 |             0 |            100 |            0 |
| Emmanuel Christian School      |              1 |               0 |             1 |              0 |          100 |
| Eunice High School             |              1 |               1 |             0 |            100 |            0 |
| Fairbairn High School          |              6 |               6 |             0 |            100 |            0 |
| Fairmont High School           |              4 |               4 |             0 |            100 |            0 |
| Falcon College                 |              1 |               0 |             1 |              0 |          100 |
| Fish Hoek High School          |              2 |               2 |             0 |            100 |            0 |
| Fletcher High School           |              1 |               1 |             0 |            100 |            0 |
| Florida Park High School       |              1 |               1 |             0 |            100 |            0 |
| Garlandale Senior Secondary Sc |              1 |               1 |             0 |            100 |            0 |
| Gateway High School            |              1 |               1 |             0 |            100 |            0 |
| Glenvista High School          |              1 |               1 |             0 |            100 |            0 |
| Grabouw High School            |              1 |               1 |             0 |            100 |            0 |
| Grace College (Pmb)            |              1 |               1 |             0 |            100 |            0 |
| Greenside High School          |              1 |               0 |             1 |              0 |          100 |
| Greenwich College              |              1 |               1 |             0 |            100 |            0 |
| Grey Boys High School          |              2 |               1 |             1 |             50 |           50 |
| Groenvlei Senior Secondary Sch |              1 |               1 |             0 |            100 |            0 |
| Groote Schuur High School      |              4 |               3 |             1 |             75 |           25 |
| Harare Girls High School       |              1 |               0 |             1 |              0 |          100 |
| Harold Cressy Senior Sec Schoo |              2 |               2 |             0 |            100 |            0 |
| Heatherhill Creative Learning  |              1 |               1 |             0 |            100 |            0 |
| Hermanus High School           |              2 |               1 |             1 |             50 |           50 |
| Herschel School                |              4 |               3 |             1 |             75 |           25 |
| Herzlia High School            |              7 |               6 |             1 |             86 |           14 |
| Hillview High School (Wonderbo |              1 |               1 |             0 |            100 |            0 |
| Holy Rosary Convent            |              1 |               1 |             0 |            100 |            0 |
| Howick High School             |              1 |               1 |             0 |            100 |            0 |
| Hudson Park High School        |              1 |               1 |             0 |            100 |            0 |
| Huguenot High School           |              3 |               1 |             2 |             33 |           67 |
| Hyde Park High School          |              1 |               0 |             1 |              0 |          100 |
| Immaculata Senior Secondary Sc |              1 |               1 |             0 |            100 |            0 |
| International School Of Sa     |              2 |               2 |             0 |            100 |            0 |
| Islamia College                |              2 |               2 |             0 |            100 |            0 |
| Jeppe Education Centre         |              1 |               0 |             1 |              0 |          100 |
| John Wycliffe Christian High S |              1 |               1 |             0 |            100 |            0 |
| Js Skenjana Senior Secondary S |              1 |               1 |             0 |            100 |            0 |
| Kgakoa Senior Secondary School |              1 |               1 |             0 |            100 |            0 |
| Khwezi-lomso Senior Sec School |              1 |               1 |             0 |            100 |            0 |
| Kimberley Girls' High School   |              1 |               1 |             0 |            100 |            0 |
| King Edward High School        |              1 |               1 |             0 |            100 |            0 |
| Kingsmead College              |              1 |               1 |             0 |            100 |            0 |
| Krugersdorp High School        |              1 |               1 |             0 |            100 |            0 |
| Kwamfundo Sec School           |              1 |               1 |             0 |            100 |            0 |
| Kwamgaga High School           |              1 |               1 |             0 |            100 |            0 |
| Lawson Brown High School       |              1 |               1 |             0 |            100 |            0 |
| Le Bocage International School |              1 |               0 |             1 |              0 |          100 |
| Legae Academy(Mogoditshane)    |              3 |               0 |             3 |              0 |          100 |
| Livingstone Senior Secondary S |              9 |               6 |             3 |             67 |           33 |
| Lyttelton Manor High School    |              2 |               2 |             0 |            100 |            0 |
| Machabeng High School          |              1 |               0 |             1 |              0 |          100 |
| Mafemani Senior Secondary Scho |              1 |               0 |             1 |              0 |          100 |
| Marais Viljoen Tech High Schoo |              1 |               1 |             0 |            100 |            0 |
| Maritzburg College             |              3 |               3 |             0 |            100 |            0 |
| Maru A Pula School             |              1 |               1 |             0 |            100 |            0 |
| Mazowe Boys High               |              1 |               0 |             1 |              0 |          100 |
| Mbusi High School              |              1 |               1 |             0 |            100 |            0 |
| Mcauley House High School      |              1 |               1 |             0 |            100 |            0 |
| Milnerton High School          |              2 |               2 |             0 |            100 |            0 |
| Mount Edgecombe Secondary Scho |              1 |               1 |             0 |            100 |            0 |
| Mount View Ss School - Hanover |              1 |               1 |             0 |            100 |            0 |
| Msobomvu Senior Secondary Scho |              1 |               0 |             1 |              0 |          100 |
| Muizenberg High School         |              2 |               2 |             0 |            100 |            0 |
| Newcastle High School          |              1 |               1 |             0 |            100 |            0 |
| Norman Henshilwood High School |              4 |               4 |             0 |            100 |            0 |
| Northlands Girls High School   |              2 |               2 |             0 |            100 |            0 |
| Noupoort Christian Care Centre |              1 |               1 |             0 |            100 |            0 |
| Ntsu Secondary School          |              1 |               0 |             1 |              0 |          100 |
| Oranje Meisieskool             |              1 |               1 |             0 |            100 |            0 |
| Other - Africa                 |              6 |               4 |             2 |             67 |           33 |
| Other - Asia                   |              2 |               1 |             1 |             50 |           50 |
| Other - Far East               |              2 |               2 |             0 |            100 |            0 |
| Other - Mozambique             |              1 |               0 |             1 |              0 |          100 |
| Other - Namibia                |              1 |               1 |             0 |            100 |            0 |
| Other - Rsa Schools            |             15 |              12 |             3 |             80 |           20 |
| Other - Tanzania               |              3 |               2 |             1 |             67 |           33 |
| Other - Zimbabwe               |              9 |               6 |             3 |             67 |           33 |
| Our Lady Of Fatima Convent     |              1 |               1 |             0 |            100 |            0 |
| Parel Vallei High School       |              2 |               1 |             1 |             50 |           50 |
| Parklands College              |              4 |               3 |             1 |             75 |           25 |
| Paul Roos Gymnasium            |              1 |               0 |             1 |              0 |          100 |
| Pearson High School            |              1 |               1 |             0 |            100 |            0 |
| Penryn College                 |              1 |               1 |             0 |            100 |            0 |
| Pietermaritzburg Girls High Sc |              1 |               1 |             0 |            100 |            0 |
| Pinelands High School          |              4 |               3 |             1 |             75 |           25 |
| Port Shepstone High School     |              1 |               1 |             0 |            100 |            0 |
| Port Shepstone Secondary Schoo |              1 |               1 |             0 |            100 |            0 |
| Portland Senior Secondary Scho |              1 |               1 |             0 |            100 |            0 |
| Potchefstroom Gymnasium        |              1 |               1 |             0 |            100 |            0 |
| Pretoria Girls High School     |              3 |               3 |             0 |            100 |            0 |
| Prince Edward Boys School      |              2 |               1 |             1 |             50 |           50 |
| Private Candidate              |              2 |               1 |             1 |             50 |           50 |
| Progress College               |              1 |               0 |             1 |              0 |          100 |
| Reddam House                   |              7 |               5 |             2 |             71 |           29 |
| Redhill High School            |              3 |               3 |             0 |            100 |            0 |
| Regina Mundi High School       |              1 |               1 |             0 |            100 |            0 |
| Rhenish Girls High School      |              5 |               4 |             1 |             80 |           20 |
| Rhodes High School             |              2 |               1 |             1 |             50 |           50 |
| Riebeeck College               |              1 |               1 |             0 |            100 |            0 |
| Rocklands Senior Secondary Sch |              1 |               1 |             0 |            100 |            0 |
| Roedean School                 |              6 |               6 |             0 |            100 |            0 |
| Rondebosch Boys High School    |              6 |               6 |             0 |            100 |            0 |
| Rustenburg Girls High School   |             20 |              17 |             3 |             85 |           15 |
| Sans Souci High School         |              6 |               4 |             2 |             67 |           33 |
| Sastri College                 |              1 |               0 |             1 |              0 |          100 |
| School Of Tomorrow             |              1 |               1 |             0 |            100 |            0 |
| Sea Point High School          |              1 |               1 |             0 |            100 |            0 |
| Sehole Combined School         |              1 |               1 |             0 |            100 |            0 |
| Selly Park Convent Secondary S |              1 |               1 |             0 |            100 |            0 |
| Serima High School             |              1 |               1 |             0 |            100 |            0 |
| Settlers High School           |              4 |               2 |             2 |             50 |           50 |
| Simonstown High School         |              1 |               1 |             0 |            100 |            0 |
| Siphesihle High School         |              1 |               0 |             1 |              0 |          100 |
| Somerset College               |              2 |               1 |             1 |             50 |           50 |
| South African College High Sch |              8 |               7 |             1 |             88 |           13 |
| South Peninsula Senior Sec Sch |              7 |               4 |             3 |             57 |           43 |
| Springfield Convent            |             16 |              12 |             4 |             75 |           25 |
| Springs Girls High School      |              1 |               0 |             1 |              0 |          100 |
| St Andrew's College            |              1 |               1 |             0 |            100 |            0 |
| St Andrew's School - Bloemfont |              1 |               0 |             1 |              0 |          100 |
| St Andrew's School - Mauritius |              1 |               1 |             0 |            100 |            0 |
| St Andrew's School - Senderwoo |              2 |               2 |             0 |            100 |            0 |
| St Anne's Diocesan College     |              3 |               3 |             0 |            100 |            0 |
| St Conrad's College            |              1 |               1 |             0 |            100 |            0 |
| St Cyprian's School            |              5 |               4 |             1 |             80 |           20 |
| St Dunstans                    |              1 |               1 |             0 |            100 |            0 |
| St George's College            |              1 |               1 |             0 |            100 |            0 |
| St John's College - Jhb        |              2 |               2 |             0 |            100 |            0 |
| St John's Diocesan High School |              3 |               2 |             1 |             67 |           33 |
| St Johns High School           |              1 |               1 |             0 |            100 |            0 |
| St Joseph's College - Rondebos |              1 |               1 |             0 |            100 |            0 |
| St Martin's School             |              2 |               2 |             0 |            100 |            0 |
| St Mary's College - Jhb        |              1 |               1 |             0 |            100 |            0 |
| St Mary's Diocesan School For  |              3 |               1 |             2 |             33 |           67 |
| St Mary's Schl For Girls-Wvley |              3 |               3 |             0 |            100 |            0 |
| St Michael's School (Bloemfont |              2 |               1 |             1 |             50 |           50 |
| St Paul's College              |              1 |               1 |             0 |            100 |            0 |
| St Stithian's College          |              1 |               1 |             0 |            100 |            0 |
| St Stithian's Collegiate (Girl |              3 |               3 |             0 |            100 |            0 |
| St Teresa's (Rosebank) Convent |              1 |               1 |             0 |            100 |            0 |
| Stellenberg High School        |              1 |               1 |             0 |            100 |            0 |
| Stirling High School           |              3 |               3 |             0 |            100 |            0 |
| Strelitzia High School         |              1 |               1 |             0 |            100 |            0 |
| Sutherland High School - Wierd |              1 |               1 |             0 |            100 |            0 |
| Swelihle High School           |              1 |               1 |             0 |            100 |            0 |
| Tableview High School          |              2 |               2 |             0 |            100 |            0 |
| The Beacon Secondary School    |              1 |               1 |             0 |            100 |            0 |
| The Heritage School            |              2 |               1 |             1 |             50 |           50 |
| The Hill High School           |              1 |               1 |             0 |            100 |            0 |
| Thekwini Community College     |              1 |               1 |             0 |            100 |            0 |
| Thomas More School             |              1 |               0 |             1 |              0 |          100 |
| Tiger Kloof Educational Instit |              1 |               1 |             0 |            100 |            0 |
| Trafalgar Senior Secondary Sch |              1 |               1 |             0 |            100 |            0 |
| Trinityhouse High School       |              1 |               1 |             0 |            100 |            0 |
| Umtata High School             |              1 |               1 |             0 |            100 |            0 |
| Vainona High School            |              1 |               1 |             0 |            100 |            0 |
| Victoria Park High School      |              1 |               1 |             0 |            100 |            0 |
| Visitation High School         |              1 |               0 |             1 |              0 |          100 |
| Voortrekker High School - Keni |              1 |               1 |             0 |            100 |            0 |
| Waldorf School                 |              1 |               1 |             0 |            100 |            0 |
| Waterford Kamhlaba College     |              1 |               1 |             0 |            100 |            0 |
| Waverley Girls High School     |              2 |               2 |             0 |            100 |            0 |
| Westerford High School         |             20 |              13 |             7 |             65 |           35 |
| Westridge High School          |              1 |               0 |             1 |              0 |          100 |
| Westville Girls High School    |              7 |               6 |             1 |             86 |           14 |
| Windhoek High School           |              1 |               0 |             1 |              0 |          100 |
| Windhoek International High Sc |              1 |               1 |             0 |            100 |            0 |
| Windsor High School - Lansdown |              1 |               0 |             1 |              0 |          100 |
| Winterberg Agricultural High S |              1 |               0 |             1 |              0 |          100 |
| Woodridge College              |              1 |               1 |             0 |            100 |            0 |
| Wrenchville High School        |              1 |               1 |             0 |            100 |            0 |
| Wykeham Collegiate             |              2 |               1 |             1 |             50 |           50 |
| Wynberg Boys' High School      |              7 |               5 |             2 |             71 |           29 |
| Wynberg Girls High School      |             16 |              13 |             3 |             81 |           19 |
| Y2k College                    |              2 |               2 |             0 |            100 |            0 |
+--------------------------------+----------------+-----------------+---------------+----------------+--------------+
220 rows in set (0.00 sec)

```
# Query 17

DELETE FROM Matric WHERE Year<2000;


Query OK, 5 rows affected (0.03 sec)


# Query 18


INSERT INTO uniData (StuID, Course, Percent, Symbol, Credits, Senior, Science) VALUES (1001,"ZOO1",0,"AB",18,"N","Y");

Just to show that is is in the table and code worked
```
mysql> SELECT * FROM uniData WHERE StuID = 1001;
+-------+--------+---------+--------+---------+--------+---------+
| StuID | Course | Percent | Symbol | Credits | Senior | Science |
+-------+--------+---------+--------+---------+--------+---------+
| 1001  | ACCS1  |      61 | 2-     |      18 | N      | N       |
| 1001  | CS1    |      88 | 1      |      18 | N      | Y       |
| 1001  | ECO1   |      66 | 2-     |      18 | N      | N       |
| 1001  | MAM1   |       0 | AB     |      18 | N      | Y       |
| 1001  | PSY1   |      58 | 3      |      36 | N      | N       |
| 1001  | STA1   |      58 | 3      |      18 | N      | Y       |
| 1001  | ZOO1   |       0 | AB     |      18 | N      | Y       |
+-------+--------+---------+--------+---------+--------+---------+
7 rows in set (0.00 sec)
```

# Query 19

UPDATE Matric 
SET UCTScore = 10*UCTScore+30;

mysql> UPDATE Matric
    -> SET UCTScore = 10*UCTScore+30;
Query OK, 516 rows affected (0.06 sec)
Rows matched: 516  Changed: 516  Warnings: 0

















