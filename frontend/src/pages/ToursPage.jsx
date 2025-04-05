import React, { useState, useEffect } from "react";
import axios from "axios";
import Navbar from "../components/Navbar";

function ToursPage() {
  const [tours, setTours] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 2;

  useEffect(() => {
    axios.get("/touragency/tours")
      .then(res => setTours(res.data))
      .catch(err => console.error("Failed to fetch tours", err));
  }, []);

  const handleBook = (tourId) => {
    axios.post("/touragency/client-tour", { tourId })
      .then(() => alert("Booked!"))
      .catch(() => alert("Booking failed"));
  };

  const indexOfLastTour = currentPage * itemsPerPage;
  const indexOfFirstTour = indexOfLastTour - itemsPerPage;
  const visibleTours = tours.slice(indexOfFirstTour, indexOfLastTour);
  const totalPages = Math.ceil(tours.length / itemsPerPage);

  const goToPage = (page) => {
    setCurrentPage(page);
  };
  

  return (
    <>
      <Navbar />
      <div className="container mt-5">
        <h2 className="mb-4">Available Tours</h2>

        <div className="row">
          {visibleTours.map(tour => (
            <div key={tour.id} className="col-md-4">
              <div className="card mb-4">
                <div className="card-body">
                  <h5 className="card-title">{tour.name}</h5>
                  <p className="card-text">Type: {tour.type}</p>
                  <p className="card-text">Price: ${tour.price}</p>
                  <button
                    onClick={() => handleBook(tour.id)}
                    className="btn btn-sm btn-primary"
                  >
                    Book
                  </button>
                  {tour.isHot && (
                    <span className="badge bg-danger mb-2 ms-2">🔥 Hot Tour</span>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="d-flex justify-content-center mt-4">
            <nav>
              <ul className="pagination">
                {Array.from({ length: totalPages }, (_, i) => (
                  <li
                    key={i}
                    className={`page-item ${currentPage === i + 1 ? "active" : ""}`}
                  >
                    <button
                      className="page-link"
                      onClick={() => goToPage(i + 1)}
                    >
                      {i + 1}
                    </button>
                  </li>
                ))}
              </ul>
            </nav>
          </div>
        )}
      </div>
    </>
  );
}

export default ToursPage;
