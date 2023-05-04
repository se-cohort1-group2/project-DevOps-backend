package sg.edu.ntu.m3project.m3project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.service.ConcertService;

@CrossOrigin
@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    ConcertService concertService;

    List<ConcertEntity> concertList;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllAvailable() {
        concertList = (List<ConcertEntity>) concertService.find("upcoming", "");
        return new ResponseEntity<List<ConcertEntity>>(concertList, HttpStatus.OK);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        concertList = concertService.find("history", "");
        return new ResponseEntity<List<ConcertEntity>>(concertList, HttpStatus.OK);

    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> search(@RequestParam String artist) {

        concertList = concertService.find("artist", artist.toUpperCase());
        return new ResponseEntity<List<ConcertEntity>>(concertList, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{concertId}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable int concertId) {
        ConcertEntity selectedConcert = concertService.findbyConcertId(concertId);
        return new ResponseEntity<ConcertEntity>(selectedConcert, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestHeader("token") String token, @RequestHeader("user-id") int userId,
            @RequestBody ConcertEntity concert) {
        return concertService.create(token, userId, concert);
    }

    @RequestMapping(value = "/edit/{concertId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestHeader("user-id") int userId, @RequestBody ConcertEntity concert,
            @PathVariable int concertId) {
        return concertService.update(userId, concert, concertId);
    }

}
