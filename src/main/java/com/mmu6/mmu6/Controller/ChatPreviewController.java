package com.mmu6.mmu6.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.security.sasl.AuthenticationException;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmu6.mmu6.Class.Authentication;
import com.mmu6.mmu6.Class.Chat;
import com.mmu6.mmu6.Class.ChatPreview;
import com.mmu6.mmu6.Class.ChatRelationship;
import com.mmu6.mmu6.Class.User;
import com.mmu6.mmu6.Class.EncrpytionClass;
import com.mmu6.mmu6.Class.userContacts;
import com.mmu6.mmu6.Respiritory.userRepository;
import com.mmu6.mmu6.Respiritory.chatRelationshipRespiritory;
import com.mmu6.mmu6.Respiritory.chatRespiritory;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/chats/preview")
@CrossOrigin(origins = "*")
public class ChatPreviewController {
	
	@Autowired
	private Authentication authenticationService;
	
    @Autowired
    private chatRespiritory chatRespiritory;
    
    @Autowired
    private chatRelationshipRespiritory chatRelationshipRespiritory;
    
    @Autowired
    private EncrpytionClass encryptionService;
    
    @GetMapping("/{id}")
   // fucntion which generates chat previews 
    public ResponseEntity<List<Chat>> getChatPreviews(@PathVariable Integer id , @RequestHeader("X-Authorization") String auth, @RequestHeader("Uid") Long uid) throws AuthenticationException {
    	authenticationService.authenticate(auth, uid);
    	// finds all chats by user id
    	List<ChatRelationship> crs = chatRelationshipRespiritory.findAllByuserId(id);
    	List<Chat> Chat = new ArrayList<>();
    	if (!crs.isEmpty()) {
    		// iterates through
    		for (ChatRelationship cr : crs) {
    			// adds a chat class of the latest message to the group
    			Chat chat = chatRespiritory.findLatestMessageInChat(cr.getChatId());
    			Chat.add(chat);
    			
    		}
    		return ResponseEntity.status(HttpStatus.OK).body(Chat);
    	}else {
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Chat);
    	}
    	
    	
       
        

    }
    


 	   
  
}

