package guru.springframework.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.model.Owner;

class OwnerServiceMapTest {

	OwnerServiceMap ownerServiceMap;
	final long ownerId = 1L;
	final String lastName = "Smith";
	
	@BeforeEach
	void setUp() throws Exception {
		ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
		ownerServiceMap.save(Owner.builder().id(ownerId).lastName(lastName).build());
	}

	@Test
	void testSaveOwnerExistingId() {
		long id = 2L;
		Owner owner2 = Owner.builder().id(id).build();
		Owner savedOwner = ownerServiceMap.save(owner2);
		
		assertEquals(id, savedOwner.getId());
	}
	
	@Test
	void testSaveOwnerNoId() {
		Owner savedOwner = ownerServiceMap.save(Owner.builder().build());
		
		assertNotNull(savedOwner);
		assertNotNull(savedOwner.getId());
	}

	@Test
	void testFindAll() {
		Set<Owner> ownerSet = ownerServiceMap.findAll();
		assertEquals(1, ownerSet.size());
	}

	@Test
	void testFindByIdLong() {
		Owner owner = ownerServiceMap.findById(ownerId);
		assertEquals(ownerId, owner.getId());
	}

	@Test
	void testDeleteByIdLong() {
		ownerServiceMap.deleteById(ownerId);
		assertEquals(0, ownerServiceMap.findAll().size());
	}

	@Test
	void testDeleteOwner() {
		ownerServiceMap.delete(ownerServiceMap.findById(ownerId));
		assertEquals(0, ownerServiceMap.findAll().size());
	}

	@Test
	void testFindByLastName() {
		Owner smith = ownerServiceMap.findByLastName(lastName);
		
		assertNotNull(smith);
		assertEquals(ownerId, smith.getId());
	}

}
