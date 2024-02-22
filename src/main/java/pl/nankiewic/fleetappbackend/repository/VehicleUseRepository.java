package pl.nankiewic.fleetappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nankiewic.fleetappbackend.dto.DataTripDTO;
import pl.nankiewic.fleetappbackend.dto.DataTripUserDTO;
import pl.nankiewic.fleetappbackend.dto.DataUseDTO;
import pl.nankiewic.fleetappbackend.dto.chart.ChartDataRespondDTO;
import pl.nankiewic.fleetappbackend.dto.use.UseView;
import pl.nankiewic.fleetappbackend.entity.Vehicle;
import pl.nankiewic.fleetappbackend.entity.VehicleUse;
import pl.nankiewic.fleetappbackend.report.ReportViewFilterParam;
import pl.nankiewic.fleetappbackend.report.view.vehicle.VehicleUseReportView;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleUseRepository extends JpaRepository<VehicleUse, Long> {

    @Query(value = "SELECT u.id as id, " +
            "v.id as vehicleId, " +
            "us.id as userId, " +
            "u.trip as trip, " +
            "u.tripDate as tripDate, " +
            "u.tripType as tripType, " +
            "u.description as description " +
            "FROM VehicleUse u " +
            "JOIN u.vehicle v " +
            "JOIN u.user us " +
            "WHERE v.id = :vehicleId")
    List<UseView> findAllByVehicleId(Long vehicleId);

    @Query(value = "SELECT u.id as id, " +
            "v.id as vehicleId, " +
            "us.id as userId, " +
            "u.trip as trip, " +
            "u.tripDate as tripDate, " +
            "u.tripType as tripType, " +
            "u.description as description " +
            "FROM VehicleUse u " +
            "JOIN u.vehicle v " +
            "JOIN u.user us " +
            "WHERE us.id = :userId")
    List<UseView> findAllByUserId(Long userId);

    @Query(value = "SELECT u.id as id, " +
            "v.id as vehicleId, " +
            "us.id as userId, " +
            "u.trip as trip, " +
            "u.tripDate as tripDate, " +
            "u.tripType as tripType, " +
            "u.description as description " +
            "FROM VehicleUse u " +
            "JOIN u.vehicle v " +
            "JOIN u.user us " +
            "WHERE u.id = :useId")
    UseView findByUseId(Long useId);

    @Query(value = "SELECT u.id as id, " +
            "v.id as vehicleId, " +
            "us.id as userId, " +
            "u.trip as trip, " +
            "u.tripDate as tripDate, " +
            "u.tripType as tripType, " +
            "u.description as description " +
            "FROM VehicleUse u " +
            "JOIN u.vehicle v " +
            "JOIN u.user us " +
            "WHERE v.id = :vehicleId AND us.id = :userId")
    List<UseView> findAllByVehicleAndUser(Long vehicleId, Long userId);

    @Query("SELECT u.id as id, " +
            "v.id as vehicleId, " +
            "u.trip as trip, " +
            "u.tripDate as tripDate, " +
            "u.tripType as tripType, " +
            "u.description as description, " +
            "us.email as createdBy " +
            "FROM VehicleUse u " +
            "JOIN u.user us " +
            "JOIN u.vehicle v " +
            "WHERE u.tripDate BETWEEN :#{#param.startDate} AND :#{#param.startDate} " +
            "AND (:#{#param.userId} IS NULL OR :#{#param.userId} = us.id) " +
            "AND (:#{#param.vehicleId} IS NULL OR :#{#param.vehicleId} = v.id)")
    List<VehicleUseReportView> findVehicleUseReportViewsByParam(ReportViewFilterParam param);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataUseDTO(u.vehicle, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.user=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    List<DataUseDTO> sumOfTripByUser(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataTripDTO(u.trip, u.tripDate) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle=?1 and (u.tripDate between ?2 and ?3) " +
            "ORDER BY u.tripDate ASC")
    List<DataTripDTO> tripByVehicleAndData(Vehicle vehicle, LocalDate begin, LocalDate end);

    @Query("SELECT u.tripType, SUM(u.trip) " +
            "FROM VehicleUse u " +
            "JOIN u.vehicle v " +
            "WHERE v.id = :vehicleId and (u.tripDate between :begin and :end) " +
            "group by u.tripType")
    List<ChartDataRespondDTO> tripByVehicleAndDataAndTripType(Long vehicleId, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataTripUserDTO(u.vehicle, SUM(u.trip)) " +
            "FROM VehicleUse u " +
            "WHERE u.user.id=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    List<DataTripUserDTO> tripByVehicleAndDataAndUser(Long userId, LocalDate begin, LocalDate end);

    @Query("SELECT new pl.nankiewic.fleetappbackend.dto.DataTripUserDTO(u.vehicle, COUNT(u.id)) " +
            "FROM VehicleUse u " +
            "WHERE u.vehicle.user.id=?1 and (u.tripDate between ?2 and ?3) " +
            "group by u.vehicle")
    List<DataTripUserDTO> numberOfUsesByVehicleAndDataAndUser(Long userId, LocalDate begin, LocalDate end);
}
