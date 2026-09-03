using Microsoft.EntityFrameworkCore;
using SportsWorldAPI.Models;

namespace SportsWorldAPI.Context;

public class SportsWorldContext(DbContextOptions<SportsWorldContext> options) : DbContext(options)
{
    public DbSet<Athlete> Athletes { get; set; }
    public DbSet<Finance> Finances { get; set; }
    public DbSet<Venue> Venues { get; set; }
}