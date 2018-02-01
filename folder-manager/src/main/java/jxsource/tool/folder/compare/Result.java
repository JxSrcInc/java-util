package jxsource.tool.folder.compare;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * results of comparing src's children with compareTo's children
 *
 */
public class Result {
	
	public class Bind {
		Bind(CFile src, CFile compareTo) {
			this.src = src;
			this.compareTo = compareTo;
		}
		CFile src;
		CFile compareTo;
		@Override
		public String toString() {
			return "Bind [src=" + src + ", compareTo=" + compareTo + "]";
		}
		
	}
	// found in both and no difference. 
	protected Set<Bind> same = new HashSet<Bind>();
	// found in both but different. 
	protected Set<Bind> diff = new HashSet<Bind>();
	// missing in src - found in compareTo but not in src. The Set contains compareTo's nodes
	protected Set<CFile> missing = new HashSet<CFile>();
	// extra in src - found in src but not in compareTo. The Set contains src's nodes
	protected Set<CFile> extra = new HashSet<CFile>();

	public Set<Bind> getSame() {
		return same;
	}
	public void addSame(Collection<Bind> same) {
		this.same.addAll(same);
	}
	public void addSame(Bind same) {
		this.same.add(same);
	}
	public Set<Bind> getDiff() {
		return diff;
	}
	public void addDiff(Collection<Bind> diff) {
		this.diff.addAll(diff);
	}
	public void addDiff(Bind diff) {
		this.diff.add(diff);
	}
	public Set<CFile> getMissing() {
		return missing;
	}
	public void addMissing(Collection<CFile> missing) {
		this.missing.addAll(missing);
	}
	public void addMissing(CFile missing) {
		this.missing.add(missing);
	}
	public Set<CFile> getExtra() {
		return extra;
	}
	public void addExtra(Collection<CFile> extra) {
		this.extra.addAll(extra);
	}
	public void addExtra(CFile extra) {
		this.extra.add(extra);
	}	@Override
	public String toString() {
		return "Result [same=" + same + ", diff=" + diff + ", missing=" + missing + ", extra=" + extra + "]";
	}
}
