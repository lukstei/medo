/**
 * This class is generated by jOOQ
 */
package lst.medo.generated.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthoritiesRecord extends org.jooq.impl.TableRecordImpl<lst.medo.generated.tables.records.AuthoritiesRecord> implements org.jooq.Record2<java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -58117099;

	/**
	 * Setter for <code>public.authorities.username</code>.
	 */
	public void setUsername(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.authorities.username</code>.
	 */
	public java.lang.String getUsername() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>public.authorities.authority</code>.
	 */
	public void setAuthority(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.authorities.authority</code>.
	 */
	public java.lang.String getAuthority() {
		return (java.lang.String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return lst.medo.generated.tables.Authorities.AUTHORITIES.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return lst.medo.generated.tables.Authorities.AUTHORITIES.AUTHORITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getAuthority();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthoritiesRecord value1(java.lang.String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthoritiesRecord value2(java.lang.String value) {
		setAuthority(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthoritiesRecord values(java.lang.String value1, java.lang.String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AuthoritiesRecord
	 */
	public AuthoritiesRecord() {
		super(lst.medo.generated.tables.Authorities.AUTHORITIES);
	}

	/**
	 * Create a detached, initialised AuthoritiesRecord
	 */
	public AuthoritiesRecord(java.lang.String username, java.lang.String authority) {
		super(lst.medo.generated.tables.Authorities.AUTHORITIES);

		setValue(0, username);
		setValue(1, authority);
	}
}
