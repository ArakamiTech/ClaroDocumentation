package co.com.cat.clarodocumentation.model;

import lombok.Data;

/**
 *
 * @author Cristhian
 */
@Data
public class FileDto {
    
    private RowOfFile rowOfFile;

	public RowOfFile getRowOfFile() {
		return rowOfFile;
	}

	public void setRowOfFile(RowOfFile rowOfFile) {
		this.rowOfFile = rowOfFile;
	}
    
}
