/**
 * This class is generated by jOOQ
 */
package lst.medo.generated;

/**
 * This class is generated by jOOQ.
 *
 * Convenience access to all sequences in public
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

	/**
	 * The sequence <code>public.article_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> ARTICLE_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("article_seq", lst.medo.generated.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.author_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> AUTHOR_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("author_seq", lst.medo.generated.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

	/**
	 * The sequence <code>public.media_seq</code>
	 */
	public static final org.jooq.Sequence<java.lang.Long> MEDIA_SEQ = new org.jooq.impl.SequenceImpl<java.lang.Long>("media_seq", lst.medo.generated.Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
