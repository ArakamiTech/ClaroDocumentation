package co.com.cat.clarodocumentation;

import co.com.cat.clarodocumentation.model.FileDto;
import co.com.cat.clarodocumentation.services.ClaroDocumentationImpl;
import co.com.cat.clarodocumentation.services.IClaroDocumentation;
import java.util.List;

/**
 *
 * @author Cristhian
 */
public class ClaroDocumentation {

	public static void main(String[] args) {
		IClaroDocumentation iClaroDocumentation;
		iClaroDocumentation = new ClaroDocumentationImpl();
		List<FileDto> list = iClaroDocumentation.readArchive(iClaroDocumentation.getRoute());
		iClaroDocumentation.createReport(list);
	}
	
}
