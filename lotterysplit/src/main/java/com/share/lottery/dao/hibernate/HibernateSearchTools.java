package com.share.lottery.dao.hibernate;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.indexes.IndexReaderAccessor;

/**
 * Utility class to generate lucene queries for hibernate search and perform full reindexing.
 *
 * @author jgarcia
 */
class HibernateSearchTools {
    protected static final Log log = LogFactory.getLog(HibernateSearchTools.class);

    /**
     * Generates a lucene query to search for a given term in all the indexed fields of a class
     *
     * @param searchTerm the term to search for
     * @param searchedEntity the class searched
     * @param sess the hibernate session
     * @param defaultAnalyzer the default analyzer for parsing the search terms
     * @return
     * @throws ParseException
     */
    public static Query generateQuery(String searchTerm, Class searchedEntity, Session sess, Analyzer defaultAnalyzer) throws ParseException {
        Query qry = null;

        if (searchTerm.equals("*")) {
            qry = new MatchAllDocsQuery();
        } else {
            // Search in all indexed fields

            IndexReaderAccessor readerAccessor = null;
            IndexReader reader = null;
            try {
                FullTextSession txtSession = Search.getFullTextSession(sess);

                // obtain analyzer to parse the query:
                Analyzer analyzer;
                if (searchedEntity == null) {
                    analyzer = defaultAnalyzer;
                } else {
                    analyzer = txtSession.getSearchFactory().getAnalyzer(searchedEntity);
                }

                // search on all indexed fields: generate field list, removing internal hibernate search field name: _hibernate_class
                // TODO: possible improvement: cache the fields of each entity
                SearchFactory searchFactory = txtSession.getSearchFactory();
                readerAccessor = searchFactory.getIndexReaderAccessor();
                reader = readerAccessor.open(searchedEntity);
                Collection<String> fieldNames = reader.getFieldNames(IndexReader.FieldOption.INDEXED);
                fieldNames.remove("_hibernate_class");
                String[] fnames = new String[0];
                fnames = fieldNames.toArray(fnames);

                // To search on all fields, search the term in all fields
                String[] queries = new String[fnames.length];
                for (int i = 0; i < queries.length; ++i) {
                    queries[i] = searchTerm;
                }

                qry = MultiFieldQueryParser.parse(Version.LUCENE_35, queries, fnames, analyzer);
            } finally {
                if (readerAccessor != null && reader != null) {
                    readerAccessor.close(reader);
                }
            }
        }
        return qry;
    }

    /**
     * Regenerates the index for a given class
     *
     * @param clazz the class
     * @param sess the hibernate session
     */
    public static void reindex(Class clazz, Session sess) {
        FullTextSession txtSession = Search.getFullTextSession(sess);
        MassIndexer massIndexer = txtSession.createIndexer(clazz);
        try {
            massIndexer.startAndWait();
        } catch (InterruptedException e) {
            log.error("mass reindexing interrupted: " + e.getMessage());
        } finally {
            txtSession.flushToIndexes();
        }
    }

    /**
     * Regenerates all the indexed class indexes
     *
     * @param async true if the reindexing will be done as a background thread
     * @param sess the hibernate session
     */
    public static void reindexAll(boolean async, Session sess) {
        FullTextSession txtSession = Search.getFullTextSession(sess);
        MassIndexer massIndexer = txtSession.createIndexer();
        massIndexer.purgeAllOnStart(true);
        try {
            if (!async) {
                massIndexer.startAndWait();
            } else {
                massIndexer.start();
            }
        } catch (InterruptedException e) {
            log.error("mass reindexing interrupted: " + e.getMessage());
        } finally {
            txtSession.flushToIndexes();
        }
    }
}
