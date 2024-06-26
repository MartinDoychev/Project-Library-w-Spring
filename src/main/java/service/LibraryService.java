package service;

import dto.LibraryDTO;
import repository.LibraryRepository;
import repository.BookRepository;
import repository.UserRepository;
import library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<LibraryDTO> getAllLibraries() {
        return libraryRepository.getAllLibraries().stream()
                .map(this::convertToLibraryDTO)
                .collect(Collectors.toList());
    }

    public LibraryDTO getLibraryById(long libraryID) {
        Library library = libraryRepository.getLibraryByID(libraryID);
        if (library == null) {
            throw new RuntimeException("Library with ID: " + libraryID + " does not exist!");
        }
        return convertToLibraryDTO(library);
    }

    public void createLibrary(LibraryDTO libraryDTO) {
        Library library = convertToLibrary(libraryDTO);
        libraryRepository.createLibrary(library);
    }

    public void updateLibrary(long libraryID, LibraryDTO libraryDTO) {
        Library library = libraryRepository.getLibraryByID(libraryID);
        if (library == null) {
            throw new RuntimeException("Library with ID: " + libraryID + " does not exist!");
        }
        updateLibraryFromDTO(library, libraryDTO);
        libraryRepository.updateLibrary(library);
    }

    public void deleteLibrary(long libraryID) {
        Library library = libraryRepository.getLibraryByID(libraryID);
        if (library == null) {
            throw new RuntimeException("Library with ID: " + libraryID + " does not exist!");
        }
        libraryRepository.deleteLibrary(libraryID);
    }

    private LibraryDTO convertToLibraryDTO(Library library) {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setLibraryID(library.getLibraryID());
        libraryDTO.setLibraryName(library.getLibraryName());
        return libraryDTO;
    }

    private Library convertToLibrary(LibraryDTO libraryDTO) {
        Library library = new Library();
        library.setLibraryID(libraryDTO.getLibraryID());
        library.setLibraryName(libraryDTO.getLibraryName());
        return library;
    }

    private void updateLibraryFromDTO(Library library, LibraryDTO libraryDTO) {
        library.setLibraryName(libraryDTO.getLibraryName());
    }
}
