(ns utils
  (:import [com.github.vertical_blank.sqlformatter SqlFormatter]
           [com.github.vertical_blank.sqlformatter.languages Dialect]
           [com.github.vertical_blank.sqlformatter.core FormatConfig]))

(def dialects {:postgres Dialect/PostgreSql})

(defn format-sql
  "Formats an SQL string with optional parameters and configuration.
  Options:
  - :params      A list of parameters to interpolate in the SQL string.
  - :indent      String to use for indentation (default is two spaces).
  - :uppercase   Boolean to indicate whether to convert keywords to uppercase.
  - :dialect     The SQL dialect to use (default is Standard SQL).
  - :max-column-length Maximum column length before wrapping (default is 50).
  "
  [sql & {:keys [params indent uppercase dialect max-column-length]
          :or   {params []
                 indent "  "
                 uppercase false
                 max-column-length 50}}]
  (let [config (-> (FormatConfig/builder)
                   (.indent indent)
                   (.uppercase uppercase)
                   (.maxColumnLength max-column-length)
                   (.params (if (sequential? params)
                              (java.util.ArrayList. params)
                              (java.util.HashMap. params)))
                   .build)
        formatter (SqlFormatter/of (or (dialects dialect) Dialect/PostgreSql))]
    (.format formatter sql config)))
