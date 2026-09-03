using SportsWorldAPI.Interfaces;

namespace SportsWorldAPI.Models;

public class Venue : IVenue
{
    public int Id { get; set; }
    public string Name { get; set; } = String.Empty;
    public int Capacity { get; set; }
    public int RentPrice { get; set; }
    public string Description { get; set; } = String.Empty;

    public string Image { get; set; } = String.Empty;
    
}