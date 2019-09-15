package bss.ServiceDocument.Transport;

public class Source {
	private String srcDir;
	private Integer srcPrior;
	private String srcArchivDir;
	private String errDir;
	private Integer srcInterval;
	private Integer srcQuantToSend;
	
	public Source(String srcDir, int srcPrior, String srcArchivDir, String errDir, Integer srcInterval, Integer srcQuantToSend){
		this.srcDir = srcDir;
		this.srcPrior = srcPrior;
		this.srcArchivDir = srcArchivDir;
		this.errDir = errDir;
		this.srcInterval = srcInterval;
		this.srcQuantToSend = srcQuantToSend; 
	}
	
	@Override
	public boolean equals(Object obj){
		 if (this == obj)
	            return true;   
		 if (obj == null)
	            return false;
		 if (getClass() != obj.getClass())
	            return false;
		 Source objArg = (Source)obj;
		 if(this.getSrcPrior() != objArg.getSrcPrior()){
			 return false;
		 }
		 return true;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getSrcDir().hashCode();
        result = prime * result + this.getSrcArchivDir().hashCode();
        result = prime * result + this.getErrDir().hashCode();
        return result;
    }
	
	public String getErrDir() {
		return errDir;
	}

	public void setErrDir(String errDir) {
		this.errDir = errDir;
	}
	
	public String getSrcDir() {
		return srcDir;
	}
	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}
	public Integer getSrcPrior() {
		return srcPrior;
	}
	public void setSrcPrior(int srcPrior) {
		this.srcPrior = srcPrior;
	}
	public String getSrcArchivDir() {
		return srcArchivDir;
	}
	public void setSrcArchivDir(String srcArchivDir) {
		this.srcArchivDir = srcArchivDir;
	}

	public Integer getSrcInterval() {
		return srcInterval;
	}

	public void setSrcInterval(Integer srcInterval) {
		this.srcInterval = srcInterval;
	}

	public Integer getSrcQuantToSend() {
		return srcQuantToSend;
	}

	public void setSrcQuantToSend(Integer srcQuantToSend) {
		this.srcQuantToSend = srcQuantToSend;
	}
	
}
