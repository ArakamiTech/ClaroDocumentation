package co.com.cat.clarodocumentation.services;

import co.com.cat.clarodocumentation.model.FileDto;
import java.util.List;

/**
 *
 * @author Cristhian
 */
public interface IClaroDocumentation {
    
    public String getRoute();

    public List<FileDto> readArchive(String route);

    public void createReport(List<FileDto> list);
    
}
