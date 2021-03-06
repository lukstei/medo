/**
 * This class is generated by jOOQ
 */
package lst.medo.generated;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 896943570;

	/**
	 * The singleton instance of <code>public</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("public");
	}

	@Override
	public final java.util.List<org.jooq.Sequence<?>> getSequences() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final java.util.List<org.jooq.Sequence<?>> getSequences0() {
		return java.util.Arrays.<org.jooq.Sequence<?>>asList(
			lst.medo.generated.Sequences.ARTICLE_SEQ,
			lst.medo.generated.Sequences.AUTHOR_SEQ,
			lst.medo.generated.Sequences.MEDIA_SEQ);
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			lst.medo.generated.tables.Article.ARTICLE,
			lst.medo.generated.tables.ArticleType.ARTICLE_TYPE,
			lst.medo.generated.tables.Author.AUTHOR,
			lst.medo.generated.tables.Authorities.AUTHORITIES,
			lst.medo.generated.tables.Media.MEDIA,
			lst.medo.generated.tables.Users.USERS);
	}
}
