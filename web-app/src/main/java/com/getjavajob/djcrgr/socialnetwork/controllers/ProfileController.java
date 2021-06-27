package com.getjavajob.djcrgr.socialnetwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.getjavajob.training.karpovn.socialnetwork.common.Account;
import com.getjavajob.training.karpovn.socialnetwork.service.AccountService;
import com.getjavajob.training.karpovn.socialnetwork.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@Controller
public class ProfileController {

	private final AccountService accountService;

	public ProfileController(AccountService accountService, MessageService messageService) {
		this.accountService = accountService;
	}

	@PostMapping("/uploadXml")
	public ModelAndView uploadXml(@RequestParam("file") MultipartFile filePart) throws IOException, SQLException {
		String content = new String(filePart.getBytes(), StandardCharsets.UTF_8);
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper xmlMapper = new XmlMapper(module);
		Account account = xmlMapper.readValue(content, Account.class);
		accountService.update(account);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("account_id", account.getId());
		modelAndView.setViewName("redirect:/home");
		System.out.println(account.getPhoneNum().toString());
		return modelAndView;
	}

	@RequestMapping(value = "/downloadXml")
	public void downloadXML(HttpServletResponse response, @RequestParam Integer id) {
		Account account = accountService.readById(id);
		try {
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition",
					"attachment; filename=someFile.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(account, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/updateAcc")
	public ModelAndView updateAcc(@RequestBody String json) throws SQLException,
			IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Account account = objectMapper.readValue(json, Account.class);
		accountService.update(account);
		return profileEdit(account.getId());
	}

	@PostMapping("/picUpload")
	public ModelAndView pucUpload(@RequestParam("file") MultipartFile filePart,
	                              @RequestParam("newAccId") Integer id) throws IOException,
			SQLException {
		if (id == null) {
			id = 0;
		}
		InputStream inputStream = null;
		if (filePart != null) {
			inputStream = filePart.getInputStream();
		}
		accountService.loadPicture(id, inputStream);
		return profileEdit(id);
	}

	@GetMapping("/profileEdit")
	public ModelAndView profileEdit(@RequestParam(required = false) Integer id) throws IOException, SQLException {
		if (id == null) {
			return new ModelAndView("redirect:login");
		}
		Account account = accountService.readById(id);
		ModelAndView modelAndView = new ModelAndView("profileEdit");
		String image = accountService.getImageFromDb(id);
		if (image == null) {
			image = accountService.getImageFromDb(2);
		}
		modelAndView.addObject("image", image);
		modelAndView.addObject("currentAcc", account);
		modelAndView.addObject("idAcc", account.getId());
		if (!account.getPhoneNum().isEmpty()) {
			modelAndView.addObject("homePhone", account.getPhoneNum().get(0).getNumber());
			modelAndView.addObject("workPhone", account.getPhoneNum().get(1).getNumber());
		}
		return modelAndView;
	}
}
