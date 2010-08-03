;;
;; Traverse all text files in $PWD and write them in a jekyll-friendly
;; format.
;;
;; Expects $PWD to contain directories like yyyy/mm, containing files
;; like blog-entry-title.txt. E.g.
;;
;;    .
;;    |-- 2009
;;    |   `-- 04
;;    |       |-- an-interesting-story.txt
;;    |       `-- something-else.txt
;;    |-- 2010
;;    |   |-- 01
;;    |   |   |-- happy-new-year.txt
;;    |   |   `-- headache.txt
;;    |   `-- 08
;;    |       `-- migrating-blog.txt
;;
;; This should result in a directory structure like:
;;
;;    .
;;    `-- _posts
;;        2009-04-01-an-interesting-story.md
;;        ...
;;        2010-08-01-migrating-blog.txt
;;
;; In addition, take the first line of the file (assuming it's the title)
;; and write that into a "YAML Front Matter" section, along with a layout
;; tag. E.g.
;;
;;     First adventures in Clojure
;;
;;     I've been banging on to anyone who'd listen for ...
;;
;; becomes:
;;
;;    ---
;;    layout: post
;;    title: First adventures in Clojure
;;    ---
;;    
;;    I've been banging on to anyone who'd listen for ...
;;    


(import '(java.io File)) 
(first (filter #(re-matches #".*\.txt" %) (.list (File. "./") )))