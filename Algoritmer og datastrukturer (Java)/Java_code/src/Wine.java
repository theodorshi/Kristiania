public record Wine(double fixedAcidity,
                   double volatileAcidity,
                   double citricAcid,
                   double residualSugar,
                   double chlorides,
                   double freeSulfurDioxide,
                   double totalSulfurDioxide,
                   double density,
                   double pH,
                   double sulphates,
                   double alcohol,
                   int quality,
                   WineType type

                   /* Could have used alcohol only */
) {}
