package br.com.softctrl.utils.java;

import java.util.Date;

import com.google.gson.annotations.Expose;

class Dummy {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vlr0;
		result = prime * result + (vlr1 ? 1231 : 1237);
		result = prime * result + ((vlr10 == null) ? 0 : vlr10.hashCode());
		result = prime * result + ((vlr11 == null) ? 0 : vlr11.hashCode());
		result = prime * result + ((vlr12 == null) ? 0 : vlr12.hashCode());
		result = prime * result + vlr2;
		long temp;
		temp = Double.doubleToLongBits(vlr3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(vlr4);
		result = prime * result + vlr5;
		result = prime * result + vlr6;
		result = prime * result + ((vlr7 == null) ? 0 : vlr7.hashCode());
		result = prime * result + ((vlr8 == null) ? 0 : vlr8.hashCode());
		result = prime * result + ((vlr9 == null) ? 0 : vlr9.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dummy other = (Dummy) obj;
		if (vlr0 != other.vlr0)
			return false;
		if (vlr1 != other.vlr1)
			return false;
		if (vlr10 == null) {
			if (other.vlr10 != null)
				return false;
		} else if (!vlr10.equals(other.vlr10))
			return false;
		if (vlr11 == null) {
			if (other.vlr11 != null)
				return false;
		} else if (!vlr11.equals(other.vlr11))
			return false;
		if (vlr12 == null) {
			if (other.vlr12 != null)
				return false;
		} else if (!vlr12.equals(other.vlr12))
			return false;
		if (vlr2 != other.vlr2)
			return false;
		if (Double.doubleToLongBits(vlr3) != Double.doubleToLongBits(other.vlr3))
			return false;
		if (Float.floatToIntBits(vlr4) != Float.floatToIntBits(other.vlr4))
			return false;
		if (vlr5 != other.vlr5)
			return false;
		if (vlr6 != other.vlr6)
			return false;
		if (vlr7 == null) {
			if (other.vlr7 != null)
				return false;
		} else if (!vlr7.equals(other.vlr7))
			return false;
		if (vlr8 == null) {
			if (other.vlr8 != null)
				return false;
		} else if (!vlr8.equals(other.vlr8))
			return false;
		if (vlr9 == null) {
			if (other.vlr9 != null)
				return false;
		} else if (!vlr9.equals(other.vlr9))
			return false;
		return true;
	}

	@Expose
	public byte vlr0;

	@Expose
	public boolean vlr1;

	@Expose
	public char vlr2;

	@Expose
	public double vlr3;

	@Expose
	public float vlr4;

	@Expose
	public int vlr5;

	@Expose
	public short vlr6;

	@Expose
	public Boolean vlr7;

	@Expose
	public Double vlr8;

	@Expose
	public Float vlr9;

	@Expose
	public Integer vlr10;

	@Expose
	public Short vlr11;

	@Expose
	public Date vlr12;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dummy [vlr0=" + vlr0 + ", vlr1=" + vlr1 + ", vlr2=" + vlr2 + ", vlr3=" + vlr3 + ", vlr4=" + vlr4 + ", vlr5=" + vlr5 + ", vlr6=" + vlr6 + ", vlr7=" + vlr7 + ", vlr8=" + vlr8 + ", vlr9=" + vlr9 + ", vlr10=" + vlr10 + ", vlr11=" + vlr11 + ", vlr12=" + vlr12 + "]";
	}
}
