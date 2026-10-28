import { useState } from "react";

const API_URL = "http://localhost:8080/api/loan/check";

export default function App() {
  const [salary, setSalary] = useState("");
  const [requestedAmount, setRequestedAmount] = useState("");
  const [decision, setDecision] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setDecision(null);
    setError("");
    setLoading(true);

    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Basic " + btoa("officer:officer123"),
        },
        body: JSON.stringify({
          salary: Number(salary),
          requestedAmount: Number(requestedAmount),
        }),
      });

      if (response.status === 400) {
        throw new Error("Please enter valid numbers greater than 0.");
      }
      if (response.status === 401) {
        throw new Error("Login failed. Check the username and password.");
      }
      if (!response.ok) {
        throw new Error("Something went wrong. Please try again.");
      }

      const data = await response.json();
      setDecision(data.decision);
    } catch (err) {
      if (err instanceof TypeError) {
        setError("Cannot reach the server. Is the Spring Boot app running?");
      } else {
        setError(err.message);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8">
        <h1 className="text-2xl font-bold text-slate-800 text-center">
          Loan Eligibility Check
        </h1>
        <p className="text-slate-500 text-center mt-1 mb-6">
          Enter your details to see if your loan is approved.
        </p>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">
              Monthly Salary
            </label>
            <input
              type="number"
              min="1"
              required
              value={salary}
              onChange={(e) => setSalary(e.target.value)}
              placeholder="e.g. 40000"
              className="w-full rounded-lg border border-slate-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">
              Requested Loan Amount
            </label>
            <input
              type="number"
              min="1"
              required
              value={requestedAmount}
              onChange={(e) => setRequestedAmount(e.target.value)}
              placeholder="e.g. 100000"
              className="w-full rounded-lg border border-slate-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white font-semibold rounded-lg py-2 transition"
          >
            {loading ? "Checking..." : "Check Eligibility"}
          </button>
        </form>

        {decision && (
          <div
            className={`mt-6 rounded-lg p-4 text-center text-lg font-bold ${
              decision === "Approved"
                ? "bg-green-100 text-green-700"
                : "bg-red-100 text-red-700"
            }`}
          >
            {decision}
          </div>
        )}

        {error && (
          <div className="mt-6 rounded-lg bg-yellow-100 text-yellow-800 p-4 text-center text-sm">
            {error}
          </div>
        )}
      </div>
    </div>
  );
}