package beaver.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import beaver.backend.domain.Trans;
// import beaver.backend.dto.Coordinates;
import beaver.backend.dto.SearchCriteria;
// import beaver.backend.service.AddressService;
// import beaver.backend.model.TransResponse;
import beaver.backend.service.TransService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HelloWorldCtrl {
    private final TransService transService;

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello!";
    }

    @PostMapping("/post")
    public String PostTest(@RequestBody String msg) {
        return "post success!!!"+msg;
    }

    // @PostMapping("/sqltest/{id}")
    // public ResponseEntity<TransResponse> findTrans(@PathVariable long id) {
        
    //     Trans trans = transService.findById(id);

    //     return ResponseEntity.ok().body(new TransResponse(trans));
        
    // }

    @PostMapping("/search")
    public ResponseEntity<List<Trans>> search(@RequestBody SearchCriteria criteria) {
        List<Trans> result = transService.searchTrans(criteria);
        return ResponseEntity.ok().body(result);
    }

    // @RestController
    // public class AddressController {

    //     private final AddressService addressService;

    //     public AddressController(AddressService addressService) {
    //         this.addressService = addressService;
    //     }

    //     @GetMapping("/address")
    //     public Coordinates address(){
    //         return addressService.getCoordinate();
    //     }
    // }
}