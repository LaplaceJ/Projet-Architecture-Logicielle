package ejb.sessions;

import java.util.Comparator;

import ejb.entites.Billet;

public class SortBilletComparator implements Comparator<Billet> {

	@Override
	public int compare(Billet arg0, Billet arg1) {
		if (arg0.getIdBillet() < arg1.getIdBillet())
			return -1;
		else if (arg0.getIdBillet() == arg1.getIdBillet())
			return 0;
		return +1;
	}

}
