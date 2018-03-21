package com.ebook.ui.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ebook.common.dto.PartDTO;
import com.ebook.common.dto.SectionDTO;
import com.ebook.domain.entity.Part;
import com.ebook.services.service.PartService;
import com.ebook.ui.mail.AWSSendMail;


@RestController // Need to include jackson formattor to get xml/json as needed.
@RequestMapping(value = PartController.PART)
public class PartController extends AbstractController<PartDTO, PartService>  {
	public static final String PART = "part";

	private static final Logger LOGGER = LoggerFactory.getLogger(PartController.class);
	@Autowired
	public PartController(PartService service) {
		super(service);
	}

	@RequestMapping(path = "savePart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public PartDTO savePart(@RequestBody PartDTO partDTO) {
		LOGGER.debug("Saving Part details ");
			
			Part  part;
			if(partDTO !=null && partDTO.getPartNumber() !=null) {
				part = service.getPartByPartNumber(partDTO.getPartNumber());
				if(part !=null) {
					LOGGER.info("Part Already Exists , Hence Couldnt add a new Entry {}", part.getId() );
				}else {
					LOGGER.info("Part Doesnt Exists , Creating a new Entry");
					
					//Checking is Sections Exists , If yes do a explicit mapping so that we have part_Id relation in db
					if(partDTO.getSections() !=null && !partDTO.getSections().isEmpty()) {
						Set<SectionDTO> sectionDTOs = partDTO.getSections();
						
						for(SectionDTO sectionDTO :sectionDTOs) {
							sectionDTO.setPart(partDTO);
						}
					
					}
					partDTO = service.save(partDTO);
					
					AWSSendMail.sendEmail();
					System.out.println("Saved DTO" + partDTO);
				}
			}
			return partDTO;
			
		}
	
	
	
	
	@RequestMapping(path = "partNumbers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PartDTO> getPartNumbers() {
		System.out.println("getPartNumbers");
		List<PartDTO> partDTOList = (List<PartDTO>) service.getAll();
		
		System.out.println("partDTO list is " + partDTOList);
	
		return partDTOList;
	}
	
	
	@RequestMapping(path = "partHeading", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  Part getPartHeading(@RequestBody PartDTO partNumber) {
		System.out.println("getPartHeading");
		
		Part partDto = service.getPartByPartNumber(partNumber.getPartNumber());
		
		System.out.println("partDTO list is " + partDto);
	
		return partDto;
	}
	
}
