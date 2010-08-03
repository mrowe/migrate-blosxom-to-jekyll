" 
Traverse all text files referenced in the blosxom entry cache and
write them out in the format expected by jekyll. The entry cache is
made up of lines like:

    '/Users/mrowe/Documents/Writing/mikerowecode/2007/10/job_search_update.txt' => 1193141280

which contain the file path and its timestamp (in seconds from epoch).
This should be mapped to an output file with the date in the name:

    2007-10-23-job_search_update.md

Call the program with the path to the blosxom entry cache on the
command line. E.g.:

    clj import_blog.clj blosxom/plugins/state/.entries_index.index
"

(use 'clojure.contrib.str-utils)
(use 'clojure.contrib.duck-streams)

(import 'java.util.Date 'java.text.SimpleDateFormat)

(def entry-index
  (read-lines (first *command-line-args*)))

(defn parse-line [line]
  (let [[_ filename timestamp] (re-matches #".*'(.+)'.*\s+(\d+).*" line)]
    {:filepath filename :timestamp timestamp}))

(defn date [timestamp] (Date. (* 1000 (Long/valueOf timestamp))))
(defn date-str [date] (. (SimpleDateFormat. "yyyy-MM-dd") format date))
(defn filename [path] (last (re-split #"/" path)))
(defn md-ext [s] (re-sub #".txt$" ".md" s))
(defn valid? [line] (not (nil? (:timestamp line))))

(defn target-file-name [entry]
  (str (date-str (date (entry :timestamp))) "-" (md-ext (filename (entry :filepath)))))

(def entries (filter valid? (map parse-line entry-index)))

(defn copy-command [entry]
  (str "cp " (entry :filepath) " " (target-file-name entry)))

(println (str-join "\n" (map copy-command entries)))
