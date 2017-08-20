package com.valtech.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.valtech.file.exception.StorageFileNotFoundException;
import com.valtech.file.object.StreetReport;

@Controller
public class FileUploadController {

	private final FileService fileProcessService;

	@Autowired
	public FileUploadController(FileService fileService) {
		this.fileProcessService = fileService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files",
				fileProcessService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = fileProcessService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		StreetReport streetReport = fileProcessService.processFile(file);
		if(null!= streetReport){
		redirectAttributes.addFlashAttribute("message",	"File Processing Report");
		redirectAttributes.addFlashAttribute("message1",	"The file uploaded is valid");
		redirectAttributes.addFlashAttribute("message2", "Total count of houses on street= " + streetReport.getTotalCount() + "\n ");
		redirectAttributes.addFlashAttribute("message3", "Houses are on the left hand (north) side of the street = "
				+ streetReport.getLeftOddNorthCount() + "\n ");
		redirectAttributes.addFlashAttribute("message4", "Houses are on the right hand (south) side of the street = "
				+ streetReport.getRightEvenSouthCount());
		redirectAttributes.addFlashAttribute("message5", "Delivery approach 1 , west to east and then east to west");
		redirectAttributes.addFlashAttribute("message6", "West to east delivery order = = "
				+ streetReport.getLeftOddNorthSideHouses());
		redirectAttributes.addFlashAttribute("message7", "Then east to west delivery order = "
				+ streetReport.getRightEvenSouthSideHouses());
		redirectAttributes.addFlashAttribute("message8", "Road Crossed count : 1 ");
		redirectAttributes.addFlashAttribute("message9", "Delivery approach 2 to deliver in sequence  ");
		redirectAttributes.addFlashAttribute("message10", " Delivery in sequence"
				+ streetReport.getDeliveryOrderOpt2());
		redirectAttributes.addFlashAttribute("message11", "Road Crossed count : " + streetReport.getCrossoverCount());
		}
		else {
			redirectAttributes.addFlashAttribute("message",	"File Processing failed");
		}

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
