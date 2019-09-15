package bss.ServiceDocument.Transport;

import java.util.Comparator;

public class SourceComparable implements Comparator<Source> {

	public int compare(Source object1, Source object2) {
		return object1.getSrcPrior().compareTo(object2.getSrcPrior());
	}

}
