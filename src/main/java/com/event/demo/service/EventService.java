package com.event.demo.service;

import com.event.demo.model.Event;
import com.event.demo.model.User;
import com.event.demo.repository.EventRepository;
import com.event.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Event
    public Event createEvent(Long userId, Event event) {
        logger.info("Attempting to create event for user ID: {}", userId);

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            event.setUser(user.get());
            logger.info("Saving event: {}", event);
            return eventRepository.save(event);  // Save the event only once
        }
        logger.error("User not found with ID: {}", userId);
        throw new RuntimeException("User not found!");
    }

    // Get Event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Get All Events
    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Update Event
    public Event updateEvent(Long id, Event eventDetails) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setTitle(eventDetails.getTitle());
            event.setLocation(eventDetails.getLocation());
            event.setDate(eventDetails.getDate());
            return eventRepository.save(event);
        }
        throw new RuntimeException("Event not found!");
    }

    // Delete Event
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
