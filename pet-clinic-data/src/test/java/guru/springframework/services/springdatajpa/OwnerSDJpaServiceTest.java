package guru.springframework.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.model.Owner;
import guru.springframework.repositories.OwnerRepository;
import guru.springframework.repositories.PetRepository;
import guru.springframework.repositories.PetTypeRepository;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
	@Mock
	OwnerRepository ownerRepository;

	@Mock
	PetRepository petRepository;
	
	@Mock
	PetTypeRepository petTypeRepository;
	
	@InjectMocks
	OwnerSDJpaService service;
	
	private final String lastName = "Smith";
	private final Long ownerId = 1L;
	
	Owner returnOwner;
	
	@BeforeEach
	void setUp() throws Exception {
		returnOwner = Owner.builder().id(ownerId).lastName(lastName).build();
	}

	@Test
	void testFindAll() {
		Set<Owner> returnOwnerSet = new HashSet<>();
		returnOwnerSet.add(Owner.builder().id(ownerId).build());
		returnOwnerSet.add(Owner.builder().id(2L).build());
		
		when(ownerRepository.findAll()).thenReturn(returnOwnerSet);
		
		Set<Owner> owners = service.findAll();
		
		assertNotNull(owners);
		assertEquals(2, owners.size());
	}

	@Test
	void testFindById() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
		
		Owner owner = service.findById(ownerId);
		
		assertNotNull(owner);
	}

	@Test
	void testSave() {
		Owner ownerToSave = Owner.builder().id(ownerId).build();
		
		when(ownerRepository.save(any())).thenReturn(returnOwner);
		Owner savedOwner = service.save(ownerToSave);
		assertNotNull(savedOwner);
	}

	@Test
	void testDelete() {
		service.delete(returnOwner);
		verify(ownerRepository, times(1)).delete(any());
	}

	@Test
	void testDeleteById() {
		service.deleteById(ownerId);
		
		verify(ownerRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void testFindByLastName() {
		when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
		Owner smith = service.findByLastName(lastName);
		assertEquals(lastName, smith.getLastName());
		verify(ownerRepository).findByLastName(any());
	}

}
