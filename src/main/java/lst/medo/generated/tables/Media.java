/**
 * This class is generated by jOOQ
 */
package lst.medo.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Media extends org.jooq.impl.TableImpl<lst.medo.generated.tables.records.MediaRecord> {

	private static final long serialVersionUID = 76516154;

	/**
	 * The singleton instance of <code>public.media</code>
	 */
	public static final lst.medo.generated.tables.Media MEDIA = new lst.medo.generated.tables.Media();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<lst.medo.generated.tables.records.MediaRecord> getRecordType() {
		return lst.medo.generated.tables.records.MediaRecord.class;
	}

	/**
	 * The column <code>public.media.id</code>.
	 */
	public final org.jooq.TableField<lst.medo.generated.tables.records.MediaRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.media.name</code>.
	 */
	public final org.jooq.TableField<lst.medo.generated.tables.records.MediaRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

	/**
	 * Create a <code>public.media</code> table reference
	 */
	public Media() {
		this("media", null);
	}

	/**
	 * Create an aliased <code>public.media</code> table reference
	 */
	public Media(java.lang.String alias) {
		this(alias, lst.medo.generated.tables.Media.MEDIA);
	}

	private Media(java.lang.String alias, org.jooq.Table<lst.medo.generated.tables.records.MediaRecord> aliased) {
		this(alias, aliased, null);
	}

	private Media(java.lang.String alias, org.jooq.Table<lst.medo.generated.tables.records.MediaRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, lst.medo.generated.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<lst.medo.generated.tables.records.MediaRecord, java.lang.Integer> getIdentity() {
		return lst.medo.generated.Keys.IDENTITY_MEDIA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<lst.medo.generated.tables.records.MediaRecord> getPrimaryKey() {
		return lst.medo.generated.Keys.MEDIA_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<lst.medo.generated.tables.records.MediaRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<lst.medo.generated.tables.records.MediaRecord>>asList(lst.medo.generated.Keys.MEDIA_PKEY, lst.medo.generated.Keys.MEDIA_NAME_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public lst.medo.generated.tables.Media as(java.lang.String alias) {
		return new lst.medo.generated.tables.Media(alias, this);
	}

	/**
	 * Rename this table
	 */
	public lst.medo.generated.tables.Media rename(java.lang.String name) {
		return new lst.medo.generated.tables.Media(name, null);
	}
}
