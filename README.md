# CENG303 Programming Assignment #2

Since the fetching data takes a significant amount of time, we also included responses in `responses.zip`.
You can extract them into `cache` file to save time and delete some of the files to check if the program can fetch data
from the api.

## Usage

1) Run pa2.jar
   ```
   java -jar pa2.jar
   ```
2) Create JAR file & run
   ```
   javac -cp lib/gson-2.10.1.jar -d out src/main/java/com/github/rhyrak/*.java src/main/java/com/github/rhyrak/model/*.java src/main/java/com/github/rhyrak/sorter/*.java
   jar cvfm pa2.jar META-INF/MANIFEST.MF -C out com
   java -jar pa2.jar
   ```

---

## How loading data works:

1. Look for a cached response body in `./cache` directory
    - cached file name format: rpp{results per page}si{start index}.json
    - If exists, load it
    - else:
2. Send an HTTP request to server
    - If response status is not 200 (OK) wait 10 seconds and retry
3. Try parsing response body
    - If success, cache the response body

---

## Group Members

* 19050111022, FURKAN DEMİR
* 20050111011, İBRAHİM BAHÇA
* 20050111034, MERTER ÇOBAN
* 20050111008, SELÇUK GENÇAY
* 21050141038, YOUSIF HARITH SUHAIL SUHAIL

---

### Output

Output of `java -jar pa2.jar` with 1 missing cached file (start index 38000):
<img src="output.png"  alt="screenshot of console showing the result of program."/>
