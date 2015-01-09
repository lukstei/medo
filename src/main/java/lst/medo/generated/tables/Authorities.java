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
public class Authorities extends org.jooq.impl.TableImpl<lst.medo.generated.tables.records.AuthoritiesRecord> {

	private static final long serialVersionUID = -1668986535;

	/**
	 * The singleton instance of <code>public.authorities</code>
	 */
	public static final lst.medo.generated.tables.Authorities AUTHORITIES = new lst.medo.generated.tables.Authorities();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<lst.medo.generated.tables.records.AuthoritiesRecord> getRecordType() {
		return lst.medo.generated.tables.records.AuthoritiesRecord.class;
	}

	/**
	 * The column <code>public.authorities.username</code>.
	 */
	public final org.jooq.TableField<lst.medo.generated.tables.records.AuthoritiesRecord, java.lang.String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

	/**
	 * The column <code>public.authorities.authority</code>.
	 */
	public final org.jooq.TableField<lst.medo.generated.tables.records.AuthoritiesRecord, java.lang.String> AUTHORITY = createField("authority", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

	/**
	 * Create a <code>public.authorities</code> table reference
	 */
	public Authorities() {
		this("authorities", null);
	}

	/**
	 * Create an aliased <code>public.authorities</code> table reference
	 */
	public Authorities(java.lang.String alias) {
		this(alias, lst.medo.generated.tables.Authorities.AUTHORITIES);
	}

	private Authorities(java.lang.String alias, org.jooq.Table<lst.medo.generated.tables.records.AuthoritiesRecord> aliased) {
		this(alias, aliased, null);
	}

	private Authorities(java.lang.String alias, org.jooq.Table<lst.medo.generated.tables.records.AuthoritiesRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, lst.medo.generated.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<lst.medo.generated.tables.records.AuthoritiesRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<lst.medo.generated.tables.records.AuthoritiesRecord, ?>>asList(lst.medo.generated.Keys.AUTHORITIES__FK_AUTHORITIES_USERS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public lst.medo.generated.tables.Authorities as(java.lang.String alias) {
		return new lst.medo.generated.tables.Authorities(alias, this);
	}

	/**
	 * Rename this table
	 */
	public lst.medo.generated.tables.Authorities rename(java.lang.String name) {
		return new lst.medo.generated.tables.Authorities(name, null);
	}
}