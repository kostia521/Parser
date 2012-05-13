package ua.edu.lnu.unitest.parser.interfaces;

public interface Parser {

	public abstract char getSeparator();

	public abstract void setSeparator(char separator);

	public abstract String getInFileName();
	
	public abstract void setInFileName(String inFileName);

	public abstract int getStartIndex();

	public abstract void setStartIndex(int startIndex);

	public abstract int getChapter();

	public abstract void setChapter(int chapter);

	public abstract int getLevel();

	public abstract void setLevel(int level);

	public abstract int getTime();

	public abstract void setTime(int time);

	public abstract int getVersion();

	public abstract void setVersion(int variant);

	public abstract int getType();

	public abstract void setType(int type);

	/**
	 * Begin parsing
	 */
	public abstract void parse();

}