namespace SportsWorldAPI.Interfaces;

interface IVenue
{
    int Id { get; set; }
    string Name { get; set; }
    int Capacity { get; set; }
    int RentPrice { get; set; }
    string Description { get; set; }
    string Image { get; set; }
}