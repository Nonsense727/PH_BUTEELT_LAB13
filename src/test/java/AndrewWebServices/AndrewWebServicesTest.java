package AndrewWebServices;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class AndrewWebServicesTest {
    private Database database;
    private RecSys recommender;
    private PromoService promoService;
    private AndrewWebServices andrewWebService;

    @Before
    public void setUp() {
        // Use in-memory database for testing (fake)
        database = new InMemoryDatabase();
        
        // Mock the recommender system (stub)
        recommender = mock(RecSys.class);
        when(recommender.getRecommendation(anyString())).thenReturn("Animal House");
        
        // Mock the promo service
        promoService = mock(PromoService.class);
        
        andrewWebService = new AndrewWebServices(database, recommender, promoService);
    }

    @Test
    public void testLogIn_Success() {
        assertTrue(andrewWebService.logIn("Scotty", 17214));
    }

    @Test
    public void testLogIn_Failure() {
        assertFalse(andrewWebService.logIn("WrongUser", 12345));
    }

    @Test
    public void testGetRecommendation() {
        // This will now run quickly with the mocked recommender
        assertEquals("Animal House", andrewWebService.getRecommendation("Scotty"));
        // Verify the recommender was called exactly once with any string
        verify(recommender, times(1)).getRecommendation(anyString());
    }

    @Test
    public void testSendPromoEmail() {
        // Test that mailTo is called when we call sendPromoEmail
        andrewWebService.sendPromoEmail("test@example.com");
        verify(promoService, times(1)).mailTo("test@example.com");
    }

    @Test
    public void testNoEmailSentAfterLogin() {
        // Verify no email is sent during login
        andrewWebService.logIn("Scotty", 17214);
        verify(promoService, never()).mailTo(anyString());
    }
}