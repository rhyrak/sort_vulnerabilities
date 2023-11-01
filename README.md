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
   
