$(document).ready(function() {
    
    
  function _validate (num, id) {
        var valid = true;

        if (num === null || num === "") {
            valid = false;
            $("#" + id).text("Please enter a number");
        }
        if (isNaN(num)) {
            valid = false;
            $("#" + id).text("Please enter a valid number");
        }
        
        if (valid) {
            if (num <2) {
                valid = false;
                $("#" + id).text("Please enter a number greater or equals 2");
            }  
        }
        return valid;
    }  
    
  function isValidInputs (lower, upper) {
      var valid = true;
      $("#lowerNumberError").text("");
      $("#upperNumberError").text("");
      
      // validate lower bound value
      if (!_validate(lower, "lowerNumberError")) {
          valid = false;
      }
      if (!_validate(upper, "upperNumberError")) {
          valid = false;
      }
      
      // check that upper bound is greater than lower bound
      if (valid) {
        if (upper <= lower) {
            valid = false;
            $("#upperNumberError").text("Upper number must be greater than lower number");
        }
      }

      return valid;
  }   

  $("#submit").on("click", function(event) {
    var lower = $("#lowerNumber").val();  
    var upper = $("#upperNumber").val();  
    var sieve = $("#sieveCheck").is(":checked");  

    if (isValidInputs(lower,upper)){
        $.ajax({
          url: "GetPrimes",
          data: {lowerNumber: lower, upperNumber: upper, sieve: sieve},
          success: function(result) {
            $("#result").html(result);
            $("#lowerNumber").val("");
            $("#upperNumber").val("");
          },
          dataType: "html",
          type: "POST"
        });
    }

    event.stopPropagation();
    event.preventDefault();
    return false;
  });

});


