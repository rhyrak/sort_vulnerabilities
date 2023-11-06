# CENG303 Programming Assignment #2

How loading data works:
1. Look for a cached response body in `./cache` directory
   - cached file name format: rpp{results per page}si{start index}.json
    - If exists, load it
    - else:
2. Send an HTTP request to server
    - If response status is not 200 (OK) wait 10 seconds and retry
3. Try parsing response body
    - If success, cache the response body
   
# Group Members 

* 19050111022, FURKAN DEMİR
* 20050111011, İBRAHİM BAHÇA
* 20050111034, MERTER ÇOBAN
* 20050111008, SELÇUK GENÇAY
* 21050141038, YOUSIF HARITH SUHAIL SUHAIL
